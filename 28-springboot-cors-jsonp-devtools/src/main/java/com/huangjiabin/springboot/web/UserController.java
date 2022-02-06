package com.huangjiabin.springboot.web;

import com.huangjiabin.springboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    @Autowired
    private User user;
    @RequestMapping("/user/getUser")
    public @ResponseBody User getUser (){
        user.setAge(20);
        user.setName("死去的王菲");
        return user;
    }
    @RequestMapping("/admin/getUser")
    public @ResponseBody String getUsera (){

        return "methods({\"name\":\"没死复活了\",\"age\":\"19\"})";
    }
}
