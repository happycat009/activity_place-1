package com.huangjiabin.springboot.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @RequestMapping(value = "/say")
    public ModelAndView say() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("message","我恁爹");
        mv.setViewName("say");
        return mv;
    }

    //也是一种写法
    @RequestMapping(value="/tall")
    public String tall(Model model){
        model.addAttribute("message","我是tall");
        return "say";
    }
}
