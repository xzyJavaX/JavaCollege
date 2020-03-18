package com.usc.renting.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPageController {
    // 后台管理员用户信息管理
    @GetMapping(value = "/admin_user")
    public String User() {
        return "admin/User";
    }

    // 后台管理员户主信息管理
    @GetMapping(value = "/admin_householder")
    public String Householder() {
        return "admin/Householder";
    }

    // 后台管路员房源信息管理
    @GetMapping(value = "/admin_house")
        public String House() {
        return "admin/House";
    }

    // 后台管理员,房源图片管理界面
    @GetMapping(value = "/admin_houseimage")
    public String HouseImage() {
        return "admin/HouseImage";
    }

    // 后台管理员 待审核户主
    @GetMapping(value = "/admin_check")
    public String Check() {
        return "admin/Check";
    }

    // 后台户主 房源信息管理界面
    @GetMapping(value = "/hadmin_house")
    public String House1() {
        return "householder/House";
    }

    // 后台户主房源图片管理界面
    @GetMapping(value = "/hadmin_houseimage")
    public String HouseImage1() {
        return "householder/HouseImage";
    }

    @GetMapping(value = "/admin_statistics")
    public String Statistics() {
        return "admin/Statistics";
    }
}