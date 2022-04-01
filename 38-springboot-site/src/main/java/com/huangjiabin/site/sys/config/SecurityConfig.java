package com.huangjiabin.site.sys.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huangjiabin.site.sys.model.User;
import com.huangjiabin.site.sys.filter.CustomFilter;
import com.huangjiabin.site.sys.filter.JwtAuthenticationTokenFilter;
import com.huangjiabin.site.sys.security.CustomUrlDecisionManager;
import com.huangjiabin.site.sys.security.RestAuthorizationEntryPoint;
import com.huangjiabin.site.sys.security.RestfulAccessDeniedHandler;
import com.huangjiabin.site.sys.service.RoleService;
import com.huangjiabin.site.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;
    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    @Autowired
    private RestAuthorizationEntryPoint restAuthorizationEntryPoint;
    @Autowired
    private RoleService roleService;
    @Autowired
    private CustomFilter customFilter;
    @Autowired
    private CustomUrlDecisionManager customUrlDecisionManager;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return username -> {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("username",username);
            User user = userService.getOne(queryWrapper);
            if(null!=user){
                user.setPassword(passwordEncoder().encode(user.getPassword()));
                user.setRoles(roleService.selectRoleByUserId(user.getId()));
                return user;
            }else {
                throw  new UsernameNotFoundException("用户名或密码错误");
            }
        };
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                //基于token,不要session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/sys/login/**","/sys/logout/**","/sys/test","/sys/user/createUser").permitAll()
                //.antMatchers("/sys/test").hasRole("a")
                .anyRequest().authenticated()
                //动态权限配置
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setAccessDecisionManager(customUrlDecisionManager);
                        o.setSecurityMetadataSource(customFilter);
                        return o;
                    }
                })
                .and()
                .headers().cacheControl();

        //添加jwt,登录授权过滤器
        http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        //添加自定义未授权和未登录返回结果
        http.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthorizationEntryPoint);

    }
    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
        return new JwtAuthenticationTokenFilter();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //可以实现不走 Spring Security 过滤器链
        web.ignoring()
                .antMatchers(

                        "/doc.html",
                                    "/webjars/**",
                                    "/swagger**/**",
                                    "/swagger-resources/**",
                                    "/v2/**",
                                    "/sys/captcha",
                                    "/sys/downloads"
                                    ,"/sys/upload"
                        ,"/sys/login/**"
                        ,"/sys/sendEmail/**"
                        ,"/sys/user/createUser"
                        ,"/sys/user//createUserStudent"
                        ,"/sys/downloadRegisterModel"
                        ,"/sys/user/createUserBatch"
                        ,"/sys/activity/getActivityPageP/**"
                        ,"/getVideo"
                        ,"/putVideo"
                        //,"/**"
                                        );
    }
}
