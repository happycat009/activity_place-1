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
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

//springboot核心注解，主要用于开启spring自动配置
//@SpringBootApplication 注解点进去有@SpringBootConfiguration注解里有@Configuration注解，所以其实这个类也是配置类，也会被创建为bean对象，也能注入依赖
//ApplicationContext context = SpringApplication.run(Application.class, args)= new ClassPathXmlApplicationContext("beans.xml");
@SpringBootApplication
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        //启动spring ioc容器第一种写法，会返回一个ApplicationContext，可以用来调getBean()拿注册到容器里的对象，然后掉对象方法
        //SpringApplication.run(Application.class, args);

        //第二种写法，是第一种拆分了两步。好处是可以配置app属性，其中配置文件也设置配置，配置文件优先级最高
        /*SpringApplication app = new SpringApplication(Application.class);
        app.setBannerMode(Banner.Mode.OFF); //这里是设置logo关闭
        app.run();*/

        /*配置文件可以对app属性配置，这里new SpringApplication()没带参数是因为在配置文件中指定了配置类
        配置文件中代码：spring.main.sources=com.huangjiabin.springboot.Application*/
        /*SpringApplication app = new SpringApplication();
        app.setBannerMode(Banner.Mode.OFF); //这里是设置logo关闭，配置文件设置了打开，配置文件优先级最高，所以logo打开
        app.run();*/

        //第三种，用流模式启动（Fluent Builder Api）
        ApplicationContext context = new SpringApplicationBuilder()
                /*注：我这里传了配置类，然而我配置文件也设置了配置类，两个配置类重名，
                然后报错 “无法注册 bean“应用程序”。已经定义了具有该名称的 bean，并且禁用了覆盖”
                然后，我又在配置文件中配置了，允许bean覆盖，所以报错解决*/
                .sources(Application.class)
                .bannerMode(Banner.Mode.OFF)
                .run(args);


    }

    //这是继承了CommandLineRunner接口重写的方法，此方法在bean创建完之后回调
    @Override
    public void run(String... args) throws Exception {
        System.out.println("bean defined"); //可以打个断点看
    }
}
