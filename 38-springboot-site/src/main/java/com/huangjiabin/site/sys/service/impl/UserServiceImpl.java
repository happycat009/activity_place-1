package com.huangjiabin.site.sys.service.impl;

import com.huangjiabin.site.sys.model.User;
import com.huangjiabin.site.sys.mapper.UserMapper;
import com.huangjiabin.site.sys.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
