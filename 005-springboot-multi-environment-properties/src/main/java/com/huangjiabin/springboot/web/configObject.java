package com.huangjiabin.springboot.web;

import com.huangjiabin.springboot.config.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class configObject {
    @Autowired
    private Student student;

    @RequestMapping(value = "/say2")
    public @ResponseBody String say(){
        return "学校："+student.getName()+"网站："+student.getWbsit();
    }

}
