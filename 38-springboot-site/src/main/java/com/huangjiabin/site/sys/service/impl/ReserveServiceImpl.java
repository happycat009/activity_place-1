package com.huangjiabin.site.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huangjiabin.site.sys.mapper.*;
import com.huangjiabin.site.sys.model.*;
import com.huangjiabin.site.sys.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huangjiabin.site.sys.util.EntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.ReflectionEntityInstantiator;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.swing.text.html.parser.Entity;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 预约表 服务实现类
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-03
 */
@Service
public class ReserveServiceImpl extends ServiceImpl<ReserveMapper, Reserve> implements ReserveService {
    @Resource
    private ReserveMapper reserveMapper;
    @Autowired
    private UserService userService;
    @Resource
    private ActivityMapper activityMapper;
    @Autowired
    private PlaceService placeService;
    @Autowired
    private ResourcesService resourcesService;
    @Autowired
    private EmailLogService emailLogService;

    @Override
    public Map getPlaceReserve(Long placeId) {
        return null;
    }

    public static LocalDateTime createTime=LocalDateTime.now();

    @Override
    public PageInfo<Reserve> getAllReserveForPage(Integer pageNum, Integer pageSize) {
        Page<Reserve> cityPage = new Page<>(pageNum, pageSize);
        PageInfo<Reserve> pageInfo = new PageInfo<>();
        pageInfo.setPageInfo(baseMapper.selectPage(cityPage, null));
        return pageInfo;

    }

    //判断是否能预定
    @Override
    public RespBean isCanReserve(Reserve reserve) {
        LocalDateTime startTime = reserve.getStartTime();
        Map<String,Object> resultMap = new HashMap();
        //判断时间
        if(startTime.isAfter(reserve.getEndTime())
                ||startTime.isBefore(LocalDateTime.now())){
            return RespBean.error("开始时间大于结束时间");
        }
        //判断预定用户是否可用
        RespBean userRespBean = userService.checkUser(reserve.getUserId());
        if(userRespBean.getCode()!=200){
            return userRespBean;
        }else {
            resultMap.put("user",userRespBean.getData());
        }
        //判断预定场地或资源是否可用
        if(reserve.getReserveTarget()==49){
            RespBean placeRespBean = placeService.checkPlaceById(reserve.getTargetId());
            if(placeRespBean.getCode()!=200){
                return placeRespBean;
            }else {
                resultMap.put("reserveTarget",placeRespBean.getData());
            }
        }else {
            RespBean resourcesRespBean = resourcesService.checkResourcesById(reserve.getTargetId());
            if(resourcesRespBean.getCode()!=200){
                return resourcesRespBean;
            }else {
                resultMap.put("reserveTarget",resourcesRespBean.getData());
            }
        }
        //判断预定是否冲突时间冲突
        List<Reserve> reserves = reserveMapper.selectNotConflict(reserve);
        if(!CollectionUtils.isEmpty(reserves)){
            return RespBean.error("时间冲突");
        }

        return RespBean.success("可以预定",resultMap);
    }


    //删除申请，逻辑删除
    @Override
    public RespBean deleteReserveByIdP(Long id) {
        Reserve reserve = reserveMapper.selectById(id);
        //取消申请
        if(cancelReserveById(id).getCode()!=200){
            return RespBean.error("删除失败");
        }
        //删除申请
        reserve.setIsDelete(1);
        reserveMapper.updateById(reserve);
        return RespBean.success("删除成功");
    }

    //取消申请
    @Override
    public RespBean cancelReserveById(Long id) {
        Reserve reserve = reserveMapper.selectById(id);
        //判断是否场地申请
        if(reserve.getReserveTarget()==49) {
            Activity activity = activityMapper.selectById(reserve.getId());
            //判断是否活动申请
            if(activity!=null){
                //停止活动
                UpdateWrapper<Activity> uwActivity = new UpdateWrapper<>();
                uwActivity.eq("reserve_id",activity.getId());
                activity.setIsStop(1);
                activityMapper.update(activity,uwActivity);
            }
        }
        //取消申请
        reserve.setIsCancel(1);
        reserveMapper.updateById(reserve);
        return RespBean.success("取消成功");
    }

    //创建申请场地或资源
    @Override
    public RespBean createReserve(Reserve reserve) {
        //判断是否能预约
        RespBean canReserve = isCanReserve(reserve);
        if(canReserve.getCode()==200){
            LocalDateTime createTime=LocalDateTime.now();
            reserve.setCreateTime(createTime);  //创建时间
            reserve.setReserveStatus(52);      //预约状态   52预约中53预定成功54预定失败
            //reserve.setReserveTarget(45);       //预约目标  49为场地 50为资源
            reserve.setIsDelete(0);             //是否删除（逻辑删除）  0为否
            reserve.setIsCancel(0);             //是否取消
            int insert = reserveMapper.insert(reserve);
            if(insert>0){
                //获取预定目标的过期时间
                Integer passTime;
                if(reserve.getReserveTarget()==49){
                    passTime = ((Place) ((Map) canReserve.getData()).get("reserveTarget")).getPassTime();
                    passTime=passTime*1000*60;
                }else {
                    passTime = ((Resources) ((Map) canReserve.getData()).get("reserveTarget")).getPassTime();
                    passTime=passTime*1000*60;
                }
                //发送邮箱
                Boolean result3 = emailLogService.sendTTLEmail(reserve,passTime);
                if(result3){
                    return RespBean.success("场地预约成功",reserve);
                }
                return RespBean.success("申请成功",reserve);
            }
            return RespBean.error("申请失败");
        }else {
            return canReserve;
        }
    }

}
