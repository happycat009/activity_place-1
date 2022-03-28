package com.huangjiabin.site.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huangjiabin.site.sys.model.ActivityB;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-20
 */
public interface ActivityBMapper extends BaseMapper<ActivityB> {
    List<ActivityB> selectActivityBByUserId(Long userId);
}
