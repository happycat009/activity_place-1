package com.huangjiabin.site.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huangjiabin.site.sys.mapper.ActivityMapper;
import com.huangjiabin.site.sys.mapper.PlaceMapper;
import com.huangjiabin.site.sys.mapper.ResourcesMapper;
import com.huangjiabin.site.sys.model.*;
import com.huangjiabin.site.sys.mapper.ReserveMapper;
import com.huangjiabin.site.sys.service.PlaceService;
import com.huangjiabin.site.sys.service.ReserveService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huangjiabin.site.sys.util.EntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.ReflectionEntityInstantiator;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.swing.text.html.parser.Entity;
import java.time.LocalDateTime;
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
    @Resource
    private PlaceMapper placeMapper;
    @Resource
    private ResourcesMapper resourcesMapper;
    @Resource
    private ActivityMapper activityMapper;
    @Override
    public Map getPlaceReserve(Long placeId) {

        return null;
    }

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
        if(startTime.isAfter(reserve.getEndTime())
                ||startTime.isBefore(LocalDateTime.now())){
            return RespBean.error("开始时间大于结束时间");
        }
        //判断预定场地或资源是否存在
        if(reserve.getReserveTarget()==49){
            Place place = placeMapper.selectById(reserve.getTargetId());
            if(place==null){
                return RespBean.error("场地不存在");
            }
        }else {
            Resources resources = resourcesMapper.selectById(reserve.getTargetId());
            if(resources==null){
                return RespBean.error("资源不存在");
            }
        }
        List<Reserve> reserves = reserveMapper.selectNotConflict(reserve);
        if(!CollectionUtils.isEmpty(reserves)){
            return RespBean.error("时间冲突");
        }
        return RespBean.success("可以预定");
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
}
