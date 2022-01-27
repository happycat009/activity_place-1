package com.huangjiabin.springboot;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        //原来的代码直接启动,要改logo的话直接在resources路径里加一个banner.txt文件就行了
        SpringApplication.run(Application.class, args);

        /*//关闭logo代码：
        SpringApplication springApplication=new SpringApplication(Application.class);
        springApplication.setBannerMode(Banner.Mode.OFF);
        springApplication.run(args);*/

        /*关闭logo:还可以在application.properties配置文件中设置
          spring.main.banner-mode=off */

    }

}
