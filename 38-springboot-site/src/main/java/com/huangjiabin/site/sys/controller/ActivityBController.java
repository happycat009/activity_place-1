package com.huangjiabin.site.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huangjiabin.site.sys.model.*;
import com.huangjiabin.site.sys.service.ActivityBService;
import com.huangjiabin.site.sys.service.HandleService;
import com.huangjiabin.site.sys.service.UserActivityService;
import com.huangjiabin.site.sys.service.UserService;
import com.huangjiabin.site.sys.util.EntityUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-20
 */
@RestController
@RequestMapping("/sys/activityB")
public class ActivityBController {
    @Autowired
    private ActivityBService activityBService;
    @Autowired
    private HandleService handleService;
    @Autowired
    private UserActivityService userActivityService;
    @Autowired
    private UserService userService;

    @ApiOperation(value = "分页查活动信息，逻辑删除查的到")
    @GetMapping("/getActivityBPageT/{current}/{size}")
    public RespBean getActivityBPageT (@PathVariable("size") Long size, @PathVariable("current") Long current){
        IPage page  = new Page();
        //设置分页的数据
        page.setCurrent(current);//页码
        page.setSize(size);// 每页的记录数
        IPage<Map<String,Object>> result = activityBService.pageMaps(page);
        return RespBean.success("true",result);
    }

    @ApiOperation(value = "分页查活动信息，逻辑删除查不到")
    @GetMapping("/getActivityBPageP/{current}/{size}")
    public RespBean getActivityBPage (@PathVariable("size") Long size, @PathVariable("current") Long current){
        QueryWrapper<ActivityB> qw = new QueryWrapper<>();
        qw.eq("is_delete",0);
        IPage page  = new Page();
        //设置分页的数据
        page.setCurrent(current);//页码
        page.setSize(size);// 每页的记录数
        IPage<Map<String,Object>> result = activityBService.pageMaps(page,qw);
        return RespBean.success("true",result);
    }

    @ApiOperation(value = "活动id查活动信息")
    @GetMapping("/getActivityBById/{id}")
    public RespBean getActivityBById (@PathVariable("id") Long id){
        ActivityB activityB = activityBService.getById(id);
        return RespBean.success("true",activityB);
    }

    @ApiOperation(value = "通过预定id查活动信息")
    @GetMapping("/getActivityBByReserveId/{reserveId}")
    public RespBean getActivityBByReserveId (@PathVariable("reserveId") Long reserveId){
        QueryWrapper<ActivityB> activityBQueryWrapper = new QueryWrapper<>();
        activityBQueryWrapper.eq("reserve_id",reserveId);
        ActivityB activityB = activityBService.getOne(activityBQueryWrapper);
        return RespBean.success("true",activityB);
    }
    @ApiOperation(value = "停止活动B")
    @PutMapping("/stopActivityB")
    public RespBean stopActivityB (@RequestBody Map map){
        Boolean result = false;
        Boolean result2 = false;
        Handle handle=null;
        LocalDateTime createTime = LocalDateTime.now();
        try {
            UpdateWrapper<ActivityB> uw = new UpdateWrapper<>();
            uw.set("is_stop",1);
            uw.eq("id",map.get("targetId"));
            result = activityBService.update(uw);
            if(result){
                handle=EntityUtil.mapToBean(map,Handle.class);
                handle.setHandle(47);
                handle.setTableName("sys_activity_b");
                handle.setTime(createTime);
                handle.setIsEmail(0);
                handle.setIsEmailSuccess(0);
                result2 = handleService.save(handle);
            }
            if(result2){
                return RespBean.success("取消成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.error("取消失败");
    }
    @ApiOperation(value = "参加活动B")
    @PutMapping("/joinActivityB")
    public RespBean joinActivityB (@RequestBody Map map){
        Boolean result;
        Boolean result2=false;
        LocalDateTime joinTime = LocalDateTime.now();
        UserActivity userActivity;
        try {
            userActivity = EntityUtil.mapToBean(map,UserActivity.class);
            userActivity.setJoinTime(joinTime);
            userActivity.setIsOut(0);
            result = userActivityService.save(userActivity);
            if(result){
                UpdateWrapper<ActivityB> uw = new UpdateWrapper<>();
                uw.setSql("join_num=join_num+1");
                uw.eq("id",userActivity.getActivityId());
                result2 = activityBService.update(uw);
            }
            if(result2){
                return RespBean.success("参加成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.error("参加失败");
    }
    @ApiOperation(value = "退出活动B")
    @PutMapping("/outActivityB")
    public RespBean outActivityB (@RequestBody Map map){
        Boolean result;
        LocalDateTime outTime = LocalDateTime.now();
        try {
           UpdateWrapper<UserActivity> uw = new UpdateWrapper<>();
           uw.set("out_time",outTime);
           uw.set("is_out",1);
           uw.eq("user_id",map.get("userId"));
           uw.eq("activity_id",map.get("activityId"));
           result = userActivityService.update(uw);
           if(result){
               return RespBean.success("退出成功");
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.error("退出失败");
    }
    @ApiOperation(value = "删除活动B逻辑删除")
    @PutMapping("/deleteActivityBP/{id}")
    public RespBean deleteActivityBP (@PathVariable("id")Long id){
        Boolean result;
        try {
            UpdateWrapper<ActivityB> uw = new UpdateWrapper<>();
            uw.set("is_delete",1);
            uw.eq("id",id);
            result = activityBService.update(uw);
            if(result){
                return RespBean.success("删除成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.error("删除失败");
    }
    @ApiOperation(value = "删除活动B物理删除")
    @PutMapping("/deleteActivityBT/{id}")
    public RespBean deleteActivityBT (@PathVariable("id")Long id){
        Boolean result;
        try {
            result = activityBService.removeById(id);
            if(result){
                return RespBean.success("删除成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.error("删除失败");
    }

}

