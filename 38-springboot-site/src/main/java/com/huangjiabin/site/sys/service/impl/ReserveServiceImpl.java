package com.huangjiabin.site.sys.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huangjiabin.site.sys.model.PageInfo;
import com.huangjiabin.site.sys.model.Reserve;
import com.huangjiabin.site.sys.mapper.ReserveMapper;
import com.huangjiabin.site.sys.service.ReserveService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
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

    @Override
    public Boolean isCanReserve(Reserve reserve) {
        List<Reserve> reserves = reserveMapper.selectNotConflict(reserve.getTargetId(), reserve.getStartTime(), reserve.getEndTime());
        System.out.println(reserves);
        LocalDateTime startTime = reserve.getStartTime();
        if(startTime.isAfter(reserve.getEndTime())
                ||startTime.isBefore(LocalDateTime.now())){
            return false;
        }
        if(!CollectionUtils.isEmpty(reserves)){
            return false;
        }
        return true;
    }
}
