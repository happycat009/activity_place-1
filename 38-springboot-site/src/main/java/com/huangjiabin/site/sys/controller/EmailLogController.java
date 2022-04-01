package com.huangjiabin.site.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.huangjiabin.site.sys.model.*;
import com.huangjiabin.site.sys.service.HandleService;
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
import java.time.LocalDateTime;
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
    @Autowired
    private HandleService handleService;
    @Value("${spring.mail.username}")
    private String username;
    @Autowired
    private UserService userService;
    @Resource
    private JavaMailSender javaMailSender;

    @RabbitListener(queues= EmailConstants.EMAIL_QUEUE_NAME)
    public void handler(Reserve reserve){
        User user = userService.getById(reserve.getUserId());
        if(user!=null){
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
            System.out.println("邮件发送成功"+ LocalDateTime.now());
        }
    }

    //延迟队列，自动通过预定
    @RabbitListener(queues= "delayed.queue")
    public void handlerP(Reserve reserve){
        RespBean userRespBean = userService.checkUser(reserve.getUserId());
        //可能用户被删除
        if(userRespBean.getCode()==200){
            Handle handle = new Handle();
            handle.setIsEmail(1);
            handle.setTargetId(reserve.getId());
            handle.setUserId(new Long(0));  //系统
            handle.setHandleTarget(55); //设置处理对象码值  55为预定
            handle.setRemark("系统自动通过");
            handle.setHandleResult(45); //设置处理结果码值  45为通过
            RespBean handleRespBean = handleService.handle(handle);
            //发邮件
            SimpleMailMessage message = new SimpleMailMessage();
            //发件人邮件地址(上面获取到的，也可以直接填写,string类型)
            message.setFrom(username);
            //要发送的qq邮箱(收件人地址)
            message.setTo(((User)userRespBean.getData()).getEmail());
            message.setSubject("校园公共活动场所管理系统！！！");
            //邮件正文
            message.setText("您在"+reserve.getCreateTime()+"预定的"+(reserve.getReserveTarget()==49?"场地":"资源")+"预定成功");//！！！
            javaMailSender.send(message);
            System.out.println("邮件发送成功"+reserve+ LocalDateTime.now());
            handleService.update(new UpdateWrapper<Handle>().set("is_email_success",1).eq("id",handle.getId()));
        }
    }
}

