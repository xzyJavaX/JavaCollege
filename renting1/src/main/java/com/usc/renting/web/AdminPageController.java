package com.usc.renting.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPageController {
    //后台用户信息管理
    @GetMapping(value="/admin_user")
    public String User(){
        return "admin/User";
    }

    //后台户主信息管理
    @GetMapping(value="/admin_householder")
    public String Householder(){
        return "admin/Householder";
    }

    //后台房源信息管理
    @GetMapping(value="/admin_house")
        public String House(){
        return "admin/House";
    }

    //后台房源图片管理界面
    @GetMapping(value = "/admin_houseimage")
    public String HouseImage(){
        return "admin/HouseImage";
    }

}