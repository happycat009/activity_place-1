package com.huangjiabin.springboot.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.awt.SunHints;

@Controller
public class IndexController {
    //自定义配置获取
    @Value("${school.name}")
    private String schoolName;
    @Value("${school.wbsit}")
    private String wbsit;
    @RequestMapping(value = "/say")
    public @ResponseBody String say(){
        return "hello"+schoolName+wbsit;
    }
}
