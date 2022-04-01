package com.huangjiabin.site.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huangjiabin.site.sys.model.RespBean;
import com.huangjiabin.site.sys.model.User;
import com.huangjiabin.site.sys.mapper.UserMapper;
import com.huangjiabin.site.sys.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

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
    @Resource
    UserMapper userMapper;

    //检查账号是否可以被注册
    @Override
    public RespBean checkUserCanCreate(User user) {
        if(user.getPassword()==null||user.getPassword()==""){
            return RespBean.error("密码为空");
        }
        if(user.getUsername()==null||user.getUsername()==""){
            return RespBean.error("用户名为空");
        }
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("username",user.getUsername());
        List<User> userList = userMapper.selectList(qw);
        if(!CollectionUtils.isEmpty(userList)){
            return RespBean.error("账号已存在");
        }
        return RespBean.success("用户名可用");
    }

    //检查用户是否可用
    @Override
    public RespBean checkUser(Long id) {
        User user = userMapper.selectById(id);
        if(user!=null){
            if(user.getDelFlag()==1){
                return RespBean.error("账号已被删除");
            }
            if(user.getDisabled()==1){
                return RespBean.error("账号已被禁用");
            }
            return RespBean.success("账号可用",user);
        }
        return RespBean.error("账号不存在");
    }
}
