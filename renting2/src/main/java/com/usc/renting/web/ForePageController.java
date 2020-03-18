package com.usc.renting.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
@Controller
public class ForePageController {
    @GetMapping(value = "/")
    public String index() {
        return "fore/chatroom";
    }

    // 普通用户注册页面
    @GetMapping(value = "/register")
    public String register() {
        return "fore/register";
    }

    // 户主注册页面
    @GetMapping(value = "/hregister")
    public String hregister() {
        return "fore/hregister";
    }

    // 登录页面
    @GetMapping(value = "/login")
    public String login() {
        return "fore/login";
    }

    // 首页
    @GetMapping(value = "/home")
    public String home() {
        return "fore/home";
    }

    /**
     * 修改密码
     * @GetMapping(value="/updatepd")
     * public String updatepd(){
     * return "fore/updatepd";
     * }
     */

    // 退出登录，跳转到首页
    @GetMapping("/forelogout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        session.removeAttribute("position");
        return "redirect:home";
    }

    // 房源详情页
    @GetMapping(value = "/house")
    public String house() {
        return "fore/house";
    }

    // 搜索结果页
    @GetMapping(value = "/search")
    public String searchResult() {
        return "fore/search";
    }

    @GetMapping(value = "/chatroom")
    public String chatRoom() {
        return "fore/chatroom";
    }

    @GetMapping(value = "/mymessage")
    public String myMessage() {
        return "fore/mymessage";
    }

    @GetMapping(value = "/top20")
    public String top20() {
        return "fore/top20";
    }

    @GetMapping(value = "/mycollection")
    public String mycollection() {
        return "fore/mycollection";
    }

    @GetMapping(value = "/myscan")
    public String myscan() {
        return "fore/myscan";
    }

    @GetMapping(value = "/myreceive")
    public String myreceive() {
        return "fore/myreceive";
    }
}
