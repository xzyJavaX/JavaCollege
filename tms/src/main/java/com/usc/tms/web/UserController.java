package com.usc.tms.web;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.usc.tms.pojo.User;
import com.usc.tms.service.UserService;
import com.usc.tms.util.Result;
import com.usc.tms.util.Page4Navigator;
import org.springframework.web.util.HtmlUtils;
import javax.servlet.http.HttpSession;
import com.usc.tms.util.Result;

@RestController
public class UserController {
    @Autowired UserService userService;

//    分页查询用户数据
    @GetMapping("/users")
    public Page4Navigator<User> list(@RequestParam(value = "start", defaultValue = "0") int start,@RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        start = start<0?0:start;
        Page4Navigator<User> page = userService.list(start,size,5);
        return page;
    }

//    增加用户数据
    @PostMapping("/users")
    public Object add(@RequestBody User bean) throws Exception{
        User user = userService.getByUsername(bean.getUsername());
        if (user==null)
        {
            userService.add(bean);
            return Result.success();
        }
        else {
            return Result.fail("已存在");
        }



    }
//删除用户数据
    @DeleteMapping("/users/{id}")
    public void delete(@PathVariable("id") int id)throws Exception {
        userService.delete(id);
    }
//根据id获取数据
    @GetMapping("/users/{id}")
    public User get(@PathVariable("id") int id) throws Exception {
        User bean=userService.get(id);
        return bean;
    }

//修改数据
    @PutMapping("/users")
    public Object update(@RequestBody User bean) throws Exception {
        userService.update(bean);
        return bean;
    }

    //登录
    @PostMapping("/forelogin")
    public Object login(@RequestBody User userParam, HttpSession session) {
        String username =  userParam.getUsername();
        username = HtmlUtils.htmlEscape(username);
        User user =userService.get(username,userParam.getPassword());
        if(null==user){
            String message ="账号密码错误";
            return Result.fail(message);
        }
        else{
//            登录成功，设置session
            session.setAttribute("user", user);

            return Result.success(user.getPosition());
        }
    }


}