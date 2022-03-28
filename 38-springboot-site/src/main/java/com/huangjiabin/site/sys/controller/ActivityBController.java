package com.huangjiabin.site.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huangjiabin.site.sys.model.ActivityB;
import com.huangjiabin.site.sys.model.RespBean;
import com.huangjiabin.site.sys.service.ActivityBService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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

}

