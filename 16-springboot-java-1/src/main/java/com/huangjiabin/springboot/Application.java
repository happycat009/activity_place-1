package com.huangjiabin.springboot;

import com.huangjiabin.springboot.service.StudentService;
import com.huangjiabin.springboot.service.impl.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

//开启所有spring配置
@SpringBootApplication
public class Application implements CommandLineRunner {  //第二种继承CommandLineRunner接口实现run方法，加入注解调用
    @Autowired
    StudentService studentService1;

    public static void main(String[] args) {

        //第一种这样调用
        /*ConfigurableApplicationContext applicationContext=SpringApplication.run(Application.class, args);
        StudentService studentService=(StudentService) applicationContext.getBean("studentServiceImpl");
        System.out.println(studentService.query());*/

        SpringApplication.run(Application.class,args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(studentService1.query());
    }
}
