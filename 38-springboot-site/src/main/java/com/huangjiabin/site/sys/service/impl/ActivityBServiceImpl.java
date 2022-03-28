package com.huangjiabin.site.sys.service.impl;

import com.huangjiabin.site.sys.mapper.ActivityBMapper;
import com.huangjiabin.site.sys.model.ActivityB;
import com.huangjiabin.site.sys.service.ActivityBService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-20
 */
@Service
public class ActivityBServiceImpl extends ServiceImpl<ActivityBMapper, ActivityB> implements ActivityBService {
    @Resource
    private ActivityBMapper activityBMapper;
    @Override
    public List<ActivityB> selectActivityBByUserId(Long userId) {
        return activityBMapper.selectActivityBByUserId(userId);
    }
}
