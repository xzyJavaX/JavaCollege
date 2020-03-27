package com.usc.renting.web;

import com.sun.org.apache.regexp.internal.RE;
import com.usc.renting.pojo.*;
import com.usc.renting.service.*;
import com.usc.renting.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ForeRESTController {
    @Autowired
    UserService userService;
    @Autowired
    HouseService houseService;
    @Autowired
    HouseImageService houseImageService;
    @Autowired
    ReviewService reviewService;

    //1.修改密码
    @PutMapping("update")
    public Object update(@RequestBody User bean) throws Exception {
        String name= bean.getName();
        String password= bean.getPassword();
//        通过HtmlUtils.htmlEscape(name);把账号里的特殊符号进行转义
        name = HtmlUtils.htmlEscape(name);
        bean.setName(name);
        boolean exist=userService.isExist(name);
        if(!exist){
            String message="此用户不存在";
            return Result.fail(message);
        }
        else {
            bean = userService.getByName(name);
        }
//        重新设置新的密码
        bean.setPassword(password);
        userService.update(bean);
        return Result.success();
    }

    //2.注册
    @PostMapping("/foreregister")
    public Object register(@RequestBody User user) {
        String name =  user.getName();
        name = HtmlUtils.htmlEscape(name);
        user.setName(name);
        boolean exist = userService.isExist(name);
        if(exist){
            String message ="用户名已经被使用,不能使用";
            return Result.fail(message);
        }
        userService.add(user);
        return Result.success();
    }

    //3.登录
    @PostMapping("/forelogin")
    public Object login(@RequestBody User userParam, HttpSession session) {
        String name =  userParam.getName();
        name = HtmlUtils.htmlEscape(name);
        User user =userService.get(name,userParam.getPassword());
        if(null==user){
            String message ="账号密码错误";
            return Result.fail(message);
        }
        else{
//            登录成功，设置session
            session.setAttribute("user", user);
            return Result.success();
        }
    }

    //4.检查前台是否登录
    @GetMapping("forecheckLogin")
    public Object checkLogin( HttpSession session) {
        User user =(User)  session.getAttribute("user");
        if(null!=user)
            return Result.success();
        return Result.fail("未登录");
    }

    //8.根据房源的hid获取单个房源信息
    @GetMapping("/forehouse/{hid}")
    public Object house(@PathVariable("hid") int hid) {
        House house = houseService.get(hid);
//        接收一个house获取对应的单个图片集合
        List<HouseImage> houseSingleImages = houseImageService.listSingleHouseImages(house);
        //        接收一个house获取对应的详情图片集合
        List<HouseImage> houseDetailImages = houseImageService.listDetailHouseImages(house);
//        把两个集合加到house对象中
        house.setHouseSingleImages(houseSingleImages);
        house.setHouseDetailImages(houseDetailImages);
//        接收House对象，获取对应的Review集合
        List<Review> reviews = reviewService.list(house);
//        把reviews中的前端用不上的数据置为null
        reviews=reviewService.getReviews(reviews);

        houseService.setReviewNumber(house);
        houseImageService.setFirstHouseImage(house);
//        因为有两个不同的对象或对象集合，改用Map存储
        Map<String,Object> map= new HashMap<>();
        map.put("house",house);
        map.put("reviews", reviews);
        return Result.success(map);
    }

   //5.搜索
    @PostMapping("foresearch")
    public Object search( String keyword){
//        如果传来的keyword为空，就把它赋值为空串
        if(null==keyword)
            keyword = "";
//        获取到前20个房源集合，0代表第0页，20代表一页20个
        List<House> ps= houseService.search(keyword,0,20);

//        设置每个房源的第一张图片，因为要在前台展示出来
        houseImageService.setFirstHouseImages(ps);
//        设置每个房源的评论数，因为要展示出来
        houseService.setReviewNumber(ps);
        return ps;
    }

    //6.评论
    @PostMapping("view")
    public Object view(HttpSession session,int hid,String content){
        House house=houseService.get(hid);
        content = HtmlUtils.htmlEscape(content);
        User user =(User)  session.getAttribute("user");
        Review review = new Review();
        review.setContent(content);
        review.setHouse(house);
        review.setUser(user);
        review.setCreateDate(new Date());
        reviewService.add(review);
        return Result.success();
    }

//7.搜索所有房源
    @GetMapping("/houses")
    public List<House> list() throws Exception {
        return houseService.list();
    }
}
