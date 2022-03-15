package com.huangjiabin.site.sys.service.impl;

import com.huangjiabin.site.sys.model.UserStudent;
import com.huangjiabin.site.sys.mapper.UserStudentMapper;
import com.huangjiabin.site.sys.service.UserStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户关联学生信息表 服务实现类
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-03
 */
@Service
public class UserStudentServiceImpl extends ServiceImpl<UserStudentMapper, UserStudent> implements UserStudentService {

}
