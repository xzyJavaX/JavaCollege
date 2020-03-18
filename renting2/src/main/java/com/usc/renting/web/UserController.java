package com.usc.renting.web;
import com.usc.renting.pojo.Householder;
import com.usc.renting.service.HouseholderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.usc.renting.pojo.User;
import com.usc.renting.service.UserService;
import com.usc.renting.util.Page4Navigator;

import javax.servlet.http.HttpSession;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    HouseholderService householderService;
    // 1.查询所有用户的分页信息
    @GetMapping("/users")
    public Page4Navigator<User> list(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "7") int size) throws Exception {
        start = start < 0 ? 0 : start;
        Page4Navigator<User> page = userService.list(start, size, 5);
        return page;
    }

    // 2.根据用户等级查询分页信息
    @GetMapping("/pusers")
    public Page4Navigator<User> list1(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "7") int size, @RequestParam(value = "position") String position) throws Exception {
        start = start < 0 ? 0 : start;
        Page4Navigator<User> page = userService.list1(start, size, 5,position);
        return page;
    }

    // 3.管理员，修改用户信息
    @PutMapping("/users")
    public void update(@RequestBody User user) throws Exception {
        userService.update(user);
    }

    // 4.管理员，根据id获取用户
    @GetMapping("/users/{id}")
    public User get(@PathVariable("id") int id) throws Exception {
        User bean = userService.get(id);
        return bean;
    }

    // 5.户主-获取登录状态下户主的全部信息
    @GetMapping("/husers")
    public User hhouseholders(HttpSession session) {
        User user1 = (User)session.getAttribute("user");
        Householder householder = householderService.findByTel(user1.getTel());
        User user = userService.get(user1.getId());
        user.setName(householder.getName());
        user.setQq(householder.getQq());
        user.setVx(householder.getVx());
        return user;
    }

    // 6.户主-修改用户（含户主）信息
    @PutMapping("husers")
    public void hupdate(@RequestBody User user1, HttpSession session) {
        User user = (User)session.getAttribute("user");
        Householder householder = householderService.findByTel(user.getTel());
        householder.setName(user1.getName());
        householder.setQq(user1.getQq());
        householder.setVx(user1.getVx());
        householderService.update(householder);
        userService.update(user1);
    }
}