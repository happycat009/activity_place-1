package com.huangjiabin.springboot;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
/*@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan*/
/*
	1、上面三个注解代替@SpringBootApplication
	2、@Import(bean.class) 导入类到容器中使成为bean对象
	3、通过在类上加@Configuration，也能把类变成容器里的bean对象。要注意该类要在main方法类同级或下级目录下
	4、@ComponentScan()、@MapperScan()等都可以扫描包使类成为容器中bean对象
	5、@ImportResource(location={"classpath:xxx.xml"})导入.xml文件使类成为容器中bean对象
	6、@SpringBootApplication(exclude={DataSourceAutoConfiguration.class}) 禁止某些自动化配置
	7、在配置文件中加：spring.autoconfigure.exclude=org.spring...DataSourceAutoConfiguration 也可以禁止，达到优化程序的效果
	8、@Lazy(value="false")使类不延迟初始化，就算全局配置了延迟初始化
	9、继承CommandLineRunner和（先)ApplicationRunner重写的run方法都会在bean创建后回调，可以做一些初始化操作
	10、springboot配置文件信息敏感加密：在test文件和配置文件看
	11、springboot默认采用HikariCp数据源比druid更快，可以在导入mybatis依赖中排除数据源
	12、修改静态资源文件位置#spring.resources.static-locations=classpath:/mysource/，已过时
*/

public class Application implements CommandLineRunner, ApplicationRunner {

	//main方法的类也是配置类，也是ioc容器里的bean对象
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("再run我，CommandLineRunner");
	}


	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("先run我，ApplicationRunner");
	}
}
