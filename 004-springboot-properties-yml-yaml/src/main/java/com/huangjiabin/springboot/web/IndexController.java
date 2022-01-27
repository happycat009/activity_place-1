package com.huangjiabin.springboot.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/*
  yml和yaml一致，都会由层级关系和properties不一样的只是展现方式不一样
  如果同时存在yml和properties那优先访问的是properties

*/

@Controller
public class IndexController {
    @RequestMapping(value = "/say")
    @ResponseBody
    public Object say(String message){
        return "Say：Hello"+message;
    }
}
