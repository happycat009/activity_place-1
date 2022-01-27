package com.huangjiabin.springboot.web;

import com.huangjiabin.springboot.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/*
    logback使用：
    1、resources目录下生成logback-spring.xml文件配置日志输出文件路径和指定包
    2、添加依赖lombok

*/
@Controller
@Slf4j
public class StudentController {
    @Autowired
    private StudentService studentService;
    @RequestMapping(value = "/student/count")
    public @ResponseBody String getStudentCount(){
        /*
        * log.trace("")
        * log.debug("")
        * log.info("")
        * log.warn("")
        * log.error("")
        *
        * */
        int num=studentService.getStudentCount();
        return "学生总人数为："+num;
    }

}
