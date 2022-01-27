package com.huangjiabin.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class IndexController {
    @RequestMapping(value = "/say")
    @ResponseBody
    public String sqy(){
        return "我日你妈";
    }

    @RequestMapping(value = "/map")
    @ResponseBody
    public Map<String,String> map(){
        Map map = new HashMap();
        map.put("徐凤年","有毛病");
        return map;
    }
}
