package com.usc.renting.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
@Controller
public class ForePageController {
    //注册页面
    @GetMapping(value="/register")
    public String register(){
        return "fore/register";
    }
    //登录页面
    @GetMapping(value="/login")
    public String login(){
        return "fore/login";
    }
    //首页
    @GetMapping(value="/home")
    public String home(){
        return "fore/home";
    }
    //修改密码
    @GetMapping(value="/updatepd")
    public String updatepd(){
        return "fore/updatepd";
    }
    //退出登录，跳转到首页
    @GetMapping("/forelogout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:home";
    }
    //房源详情页
    @GetMapping(value="/house")
    public String house(){
        return "fore/house";
    }
    //搜索结果页
    @GetMapping(value="/search")
    public String searchResult(){
        return "fore/search";
    }
}
