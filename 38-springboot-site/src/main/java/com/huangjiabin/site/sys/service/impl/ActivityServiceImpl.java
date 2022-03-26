package com.huangjiabin.site.sys.service.impl;

import com.huangjiabin.site.sys.model.Activity;
import com.huangjiabin.site.sys.mapper.ActivityMapper;
import com.huangjiabin.site.sys.service.ActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 活动信息表 服务实现类
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-25
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {

}
