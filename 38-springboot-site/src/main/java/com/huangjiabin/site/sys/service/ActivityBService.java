package com.huangjiabin.site.sys.service;

import com.huangjiabin.site.sys.model.ActivityB;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-20
 */
public interface ActivityBService extends IService<ActivityB> {
    List<ActivityB> selectActivityBByUserId(Long userId);
}
