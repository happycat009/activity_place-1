package com.huangjiabin.springboot;
/*
    三种创建springboot工程方法
    1、new project -> spring initializr default -> https://start.spring.io/
     ->group:com.huangjibin.springboot、artifact:001-springboot-first、package:com.huangjibin.springboot
    2、new project -> spring initializr custom -> https://start.aliyun.com
    阿里云创建springboot项目会用多模块管理引入springboot项目,还多了一个maven官方编译插件
    3、创建一个maven工程，父项目继承springboot就好了
*/
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//springboot核心注解，主要用于开启spring自动配置
//@SpringBootApplication 注解点进去有@SpringBootConfiguration注解里有@Configuration注解，所以其实这个类也是配置类
//ApplicationContext context = SpringApplication.run(Application.class, args)= new ClassPathXmlApplicationContext("beans.xml");
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        //启动spring ioc容器第一种写法，会返回一个ApplicationContext，可以用来调getBean()拿注册到容器里的对象，然后掉对象方法
        SpringApplication.run(Application.class, args);

        //第二种写法，是第一种拆分了两步
        SpringApplication app = new SpringApplication(Application.class);
        app.setBannerMode(Banner.Mode.OFF); //好处就是这里可以对app属性设置，这里是设置logo关闭
        app.run();

        /*
            启动也可以不传参数配置类Application.class，在配置文件中指定配置类:
            spring.main.sources=com.huangjiabin.springboot.Application
        */

    }

}
