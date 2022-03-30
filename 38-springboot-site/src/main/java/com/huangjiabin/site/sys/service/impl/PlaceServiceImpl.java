package com.huangjiabin.site.sys.service.impl;

import com.huangjiabin.site.sys.model.Place;
import com.huangjiabin.site.sys.mapper.PlaceMapper;
import com.huangjiabin.site.sys.service.PlaceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huangjiabin.site.sys.service.ReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 场地信息表 服务实现类
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-03
 */
@Service
public class PlaceServiceImpl extends ServiceImpl<PlaceMapper, Place> implements PlaceService {
    @Resource
    private ReserveService reserveService;

    //删除场地，逻辑删除
    @Override
    public Boolean deletePlaceByIdP(Long id) {

        return false;
    }
}
