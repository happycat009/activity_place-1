package com.huangjiabin.site.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huangjiabin.site.sys.model.*;
import com.huangjiabin.site.sys.service.*;
import com.huangjiabin.site.sys.util.EntityUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 预约表 前端控制器
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-03
 */
@RestController
@RequestMapping("/sys/reserve")
public class ReserveController {
    @Autowired
    private ActivityService activityService;
    @Autowired
    private ReserveService reserveService;
    @Autowired
    private ReserveHandleService reserveHandleService;
    @Autowired
    private EmailLogService emailLogService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private PlaceService placeService;

    public static LocalDateTime createTime=LocalDateTime.now();

    @ApiOperation(value = "申请场地或资源")
    @PostMapping("/createReserve")
    public RespBean placeReserve(@RequestBody Map map){
        Reserve reserve;
        try {
            reserve = EntityUtil.mapToBean(map, Reserve.class);
            //判断是否可以预定
            if(reserveService.isCanReserve(reserve).getCode()==200){
                reserve.setCreateTime(createTime);  //创建时间
                reserve.setReserveStatus(52);      //预约状态   52预约中53预定成功54预定失败
                //reserve.setReserveTarget(45);       //预约目标  49为场地 50为资源
                reserve.setIsDelete(0);             //是否删除（逻辑删除）  0为否
                reserve.setIsCancel(0);             //是否取消
                Boolean result = reserveService.save(reserve);
                if(result){
                    return RespBean.success("申请成功",reserve);
                }
            }else {
                return RespBean.error("申请失败，请核实时间");
            }
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return  RespBean.error("申请失败，请核实申请人和申请目标");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.error("申请失败");
    }

    /*
        预定场和活动
    */
    @ApiOperation(value = "申请场地和活动预定")
    @Transactional
    @PutMapping("/placeAndActivityReserve")
    public RespBean placeAndActivityReserve(@RequestBody Map map){
        Reserve reserve;
        Activity activity;
        try {
            reserve = EntityUtil.mapToBean(map, Reserve.class);
            //判断是否可以预定
            RespBean respBean = reserveService.isCanReserve(reserve);
            if(respBean.getCode()==200){
                //预定场地
                activity = EntityUtil.mapToBean(map, Activity.class);
                reserve.setCreateTime(createTime);  //创建时间
                reserve.setReserveStatus(51);      //预约状态   51预约中52预定成功53预定失败
                //reserve.setReserveTarget(45);       //预约目标  49为场地 50为资源
                reserve.setIsDelete(0);             //是否删除（逻辑删除）  0为否
                reserve.setIsCancel(0);             //是否取消
                Boolean result = reserveService.save(reserve);
                if(result){
                    //预定活动
                    activity.setCreateTime(createTime);
                    activity.setReserveId(reserve.getId());
                    Boolean result2 = activityService.save(activity);
                    Map mapResult = new HashMap();
                    mapResult.put("reserve",reserve);
                    mapResult.put("activity", activity);
                    if(result&&result2){
                        //发送邮箱
                        Boolean result3 = emailLogService.sendEmail(reserve);
                        if(result3){
                            return RespBean.success("场地预约成功",mapResult);
                        }
                    }
                }
            }else {
                return respBean;
            }
        }catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return  RespBean.error("申请失败，请核实申请人id和申请目标id");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return RespBean.error("申请失败");
    }


    @ApiOperation(value = "通过或拒绝预定操作，45通46拒绝")
    @PutMapping("/reserveHandle")
    public RespBean placeReserveHandle (@RequestBody Map map){
        ReserveHandle reserveHandle;
        try {
            reserveHandle = EntityUtil.mapToBean(map,ReserveHandle.class);
            if(reserveHandle.getHandle()==45){
                UpdateWrapper<Reserve> uw = new UpdateWrapper();
                uw.set("reserve_status",52);
                uw.eq("id",reserveHandle.getReserveId());
                reserveService.update(uw);
            }else if(reserveHandle.getHandle()==46){
                UpdateWrapper<Reserve> uw = new UpdateWrapper();
                uw.set("reserve_status",53);
                uw.eq("id",reserveHandle.getReserveId());
                reserveService.update(uw);
            }
            Boolean result = reserveHandleService.save(reserveHandle);
            if(result){
                return RespBean.success("操作成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.error("操作失败");
    }

    @ApiOperation(value = "分页查询所有预定信息")
    @GetMapping("/selectAllReserveForPage/{current}/{size}")
    public RespBean getPlaceReserveForPage(@PathVariable("size") Long size,@PathVariable("current") Long current){
        IPage<Reserve> page  = new Page();
        //设置分页的数据
        page.setCurrent(current);//页码
        page.setSize(size);// 每页的记录数
        IPage<Reserve> result = reserveService.page(page);
        return RespBean.success("true",result);
    }

    @ApiOperation(value = "获取某个场地或资源的所有预定信息")
    @GetMapping(value = "/getOneTargetAllReserve/{targetId}")
    public RespBean getAllPlaceReserve(@PathVariable("targetId") Long targetId){
        QueryWrapper<Reserve> qw = new QueryWrapper<>();
        qw.eq("target_id",targetId);
        List<Reserve> reserveList = reserveService.list(qw);
        return RespBean.success("true",reserveList);
    }

    @ApiOperation(value = "按条件查询申请信息")
    @PostMapping(value = "/selectReserveByCondition")
    public RespBean getSpecialPlaceReserve(@RequestBody Map map){
        QueryWrapper<Reserve> qw = new QueryWrapper<>();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for(Object key:map.keySet()){
            if("placeId".equals(key)){
                qw.eq("target_id",map.get(key));    //预定目标id
            }else if("reserveStatus".equals(key)){
                qw.eq("reserve_status",map.get(key));   //预定中51 成功52 失败53
            }else if("startTime".equals(key)){          //开始时间
                qw.le("end_time",LocalDateTime.parse((String)map.get(key),df)).or().
                le("start_time",LocalDateTime.parse((String)map.get(key),df));
            }else if("endTime".equals(key)){
                qw.ge("end_time",LocalDateTime.parse((String)map.get(key),df)).or().
                ge("start_time",LocalDateTime.parse((String)map.get(key),df));
            }else if("isCancel".equals(key)){
                qw.eq("is_cancel",map.get(key));
            }
        }
        List<Reserve> reserveList = reserveService.list(qw);
        return RespBean.success("true",reserveList);
    }

    @ApiOperation(value = "删除申请，逻辑删除")
    @DeleteMapping(value = "/deleteReserve/{id}")
    public RespBean deleteReserve(@PathVariable("id") Long id){
        return reserveService.deleteReserveByIdP(id);
    }

    @ApiOperation(value = "取消申请")
    @PostMapping(value = "/cancelReserve/{id}")
    public RespBean cancelReserve(@PathVariable("id") Long id){
        return reserveService.cancelReserveById(id);
    }

}

