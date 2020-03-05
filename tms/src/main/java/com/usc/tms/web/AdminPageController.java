package com.usc.tms.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpSession;


@Controller
public class AdminPageController {
    @GetMapping(value="/admin")
    public String admin(){
        return "redirect:admin_user_list";
    }
//    @GetMapping(value="/admin_user_list")
//    public String listCategory(){
//        return "admin/listCategory";
//    }
//    @GetMapping(value="/admin_category_edit")
//    public String editCategory(){
//        return "admin/editCategory";
//
//    }
@GetMapping(value="/home")
public String home(){
    return "fore/home";
}

//    管理员-用户管理
    @GetMapping(value="/admin_user")
    public String user(){
        return "admin/User";
    }

    @GetMapping(value="/admin_fixture")
    public String fixture(){
        return "admin/Fixture";
    }

    @GetMapping(value="/admin_useinfo")
    public String adminuser(){
        return "admin/Useinfo";
    }

    @GetMapping(value ="login")
    public String login(){
        return "fore/login";
    }

    @GetMapping(value="search")
    public String search(){
        return "admin/Search";
    }

    @GetMapping(value="admin_error")
    public String error(){
        return "admin/Error";
    }

    //退出登录，跳转到首页
    @GetMapping("/forelogout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:login";
    }
}