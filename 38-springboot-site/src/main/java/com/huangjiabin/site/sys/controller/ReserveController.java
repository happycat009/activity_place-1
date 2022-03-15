package com.huangjiabin.site.sys.controller;


import com.huangjiabin.site.sys.model.Reserve;
import com.huangjiabin.site.sys.model.ReserveHandle;
import com.huangjiabin.site.sys.model.RespBean;
import com.huangjiabin.site.sys.service.ReserveHandleService;
import com.huangjiabin.site.sys.service.ReserveService;
import com.huangjiabin.site.sys.util.EntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
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
    private ReserveService reserveService;
    @Autowired
    private ReserveHandleService reserveHandleService;

    @PutMapping("reservePlace")
    public RespBean reservePlace(@RequestBody Map map){
        Reserve reserve;
        LocalDateTime createTime = LocalDateTime.now();
        try {
            reserve = EntityUtil.mapToBean(map, Reserve.class);
            reserve.setCreateTime(createTime);
            Boolean result = reserveService.save(reserve);
            if(result){
                return RespBean.success("场地预约成功",reserve);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.error("场地预约失败");
    }
    @PutMapping("reserveHandle")
    public RespBean reserveHandle (@RequestBody Map map){
        ReserveHandle reserveHandle;
        try {
            reserveHandle = EntityUtil.mapToBean(map,ReserveHandle.class);
            Boolean result = reserveHandleService.save(reserveHandle);
            if(result){
                return RespBean.success("操作成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.error("操作失败");
    }

}

