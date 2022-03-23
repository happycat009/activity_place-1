package com.huangjiabin.site.sys.service.impl;

import com.huangjiabin.site.sys.model.Authority;
import com.huangjiabin.site.sys.mapper.AuthorityMapper;
import com.huangjiabin.site.sys.model.User;
import com.huangjiabin.site.sys.service.AuthorityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-03
 */
@Service
public class AuthorityServiceImpl extends ServiceImpl<AuthorityMapper, Authority> implements AuthorityService {

    @Resource
    AuthorityMapper authorityMapper;
    @Override
    public List<Authority> getAuthorityByUserId() {
       Long userId = ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
       List<Authority> authorities = authorityMapper.getAuthorityByUserId(userId);
       return authorities;
    }

    @Override
    public List<Authority> getAuthorityByRole() {

        return authorityMapper.getAuthorityWithRole();
    }
}
