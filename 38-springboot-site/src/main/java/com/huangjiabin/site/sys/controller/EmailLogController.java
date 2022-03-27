package com.huangjiabin.site.sys.controller;


import com.huangjiabin.site.sys.model.EmailConstants;
import com.huangjiabin.site.sys.model.Reserve;
import com.huangjiabin.site.sys.model.RespBean;
import com.huangjiabin.site.sys.model.User;
import com.huangjiabin.site.sys.service.UserService;
import com.huangjiabin.site.sys.util.CommonUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-26
 */
@RestController
@RequestMapping("/sys/emailLog")
public class EmailLogController {
    @Value("${spring.mail.username}")
    private String username;
    @Autowired
    private UserService userService;
    @Resource
    private JavaMailSender javaMailSender;

    @RabbitListener(queues= EmailConstants.EMAIL_QUEUE_NAME)
    public void handler(Reserve reserve){
        User user = userService.getById(reserve.getUserId());
        //发邮件
        SimpleMailMessage message = new SimpleMailMessage();
        //发件人邮件地址(上面获取到的，也可以直接填写,string类型)
        message.setFrom(username);
        //要发送的qq邮箱(收件人地址)
        message.setTo(user.getEmail());
        message.setSubject("校园公共活动场所管理系统！！！");
        //邮件正文
        message.setText("您在"+reserve.getCreateTime()+"预定的"+(reserve.getReserveTarget()==49?"场地":"资源")+"预定成功");//！！！
        javaMailSender.send(message);
    }
}

