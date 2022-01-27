package com.huangjiabin.springboot.config;

import com.huangjiabin.springboot.servlet.MyServlet2;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletConfig {
    @Bean
    /*
        @Bean方法级别注解
        相当于
        <beans>
            <bean id="" class=""/>
        </beans>
    */
    public ServletRegistrationBean servletRegistrationBean(){
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new MyServlet2(),"/servlet2");
        return servletRegistrationBean;
    }
}
