package com.huangjiabin.site.sys.controller;

import com.huangjiabin.site.sys.model.RespBean;
import com.huangjiabin.site.sys.util.HWCloudUtils;
import com.obs.services.model.ObsObject;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/sys")
public class HWCloudController {
    @Resource
    private JavaMailSender javaMailSender;
    //这一步是获取application.properties中设置的发件人邮箱地址
    @Value("${spring.mail.username}")
    private String username;

    //文件下载页面路由
//    @RequestMapping("/text")
//    public String load(){
//        return "upfile";//返回upfile.html
//    }

    //前端请求路由
    @PostMapping("/upload")
    public  RespBean upload(@RequestParam("file") MultipartFile file, HttpServletRequest req) throws IOException {
        if(file==null) return RespBean.error("上传失败，请先选择文件");
        String Key = file.getOriginalFilename();
        //  out.println(Key);
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        HWCloudUtils.ObsUpload("activity-place",Key,inputStream);
        System.out.println("上传成功"+Key);
        return RespBean.success("成功");
    }
    @GetMapping("/downloads")
    public RespBean downloads( String filename, HttpServletRequest req, HttpServletResponse rep) {
        //obs下载文件
        ObsObject obsObject = HWCloudUtils.getFile("activity-place",filename);
        InputStream inputStream = obsObject.getObjectContent();
        HWCloudUtils.dowloadFile(rep,inputStream,filename);
        return RespBean.success("成功");//重定向到当前下载页面
    }
    @GetMapping("/sandPhoneCode")
    public RespBean sandPhoneCode(String mobile,String content,HttpServletRequest request){
        HWCloudUtils.sendCode(mobile,content,request);
        return RespBean.success("短信发送成功");
    }

    @ApiOperation(value = "发送邮箱")
    @GetMapping("/sendEmail")
    public String  sendMail() { //String address
        //发邮件
        SimpleMailMessage message = new SimpleMailMessage();
        //发件人邮件地址(上面获取到的，也可以直接填写,string类型)
        message.setFrom(username);
        //要发送的qq邮箱(收件人地址)
        //message.setTo("2711198788@qq.com");//刘杰
        message.setTo("2048622419@qq.com");
        //邮件主题
        message.setSubject("激情网址，活力四射");
        //邮件正文
        message.setText("https://www.shdf.gov.cn/");//！！！
        javaMailSender.send(message);
        return "发送成功！";
    }

}
