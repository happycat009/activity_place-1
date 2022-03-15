package com.huangjiabin.site.sys.service;

import com.huangjiabin.site.sys.model.RespBean;

import javax.servlet.http.HttpServletRequest;

public interface LoginService {
    RespBean login(String username, String password,String code, HttpServletRequest request);

}
