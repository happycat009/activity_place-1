package com.huangjiabin.springboot.config;

import com.huangjiabin.springboot.filter.MyFilter2;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new MyFilter2());
        filterRegistrationBean.addUrlPatterns("/user/*"); //注这里只能定义当前包下的，/user/** 是错的，不能拦截到下级包
        return filterRegistrationBean;
    }
}
