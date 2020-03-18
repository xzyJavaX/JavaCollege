package com.usc.renting.web;

import com.usc.renting.pojo.Householder;
import com.usc.renting.pojo.User;
import com.usc.renting.pojo.House;
import com.usc.renting.service.HouseImageService;
import com.usc.renting.service.HouseholderService;
import com.usc.renting.service.HouseService;
import com.usc.renting.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@RestController
public class HouseController {
    @Autowired
    HouseService houseService;
    @Autowired
    HouseholderService householderService;
    @Autowired
    HouseImageService houseImageService;

    // 1、管理员-获取户主对应的所有房源
    @GetMapping("/householders/{hid}/houses")
    public Page4Navigator<House> list(@PathVariable("hid") int hid, @RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        start = start < 0 ? 0 : start;
        Page4Navigator<House> page =houseService.list(hid, start, size, 5 );
        houseImageService.setFirstHouseImages(page.getContent());
        return page;
    }

    // 2、
    @GetMapping("/houses/{id}")
    public House get(@PathVariable("id")int id) throws Exception {
        House bean=houseService.get(id);
        return bean;
    }

    // 3、管理员-添加房源信息
    @PostMapping("/houses")
    public Object add(@RequestBody House bean) throws Exception {
        bean.setCreateDate(new Date());
        bean.setState("上架");
        houseService.add(bean);
        return bean;
    }

    // 4、删除房源，包括底下的图片
    @DeleteMapping("/houses/{id}")
    public void delete(@PathVariable("id") int id) throws Exception {
        houseService.delete(id);
    }

    // 5、管理员，户主-修改房源信息
    @PutMapping("/houses")
    public Object update(@RequestBody House bean) throws Exception {
        houseService.update(bean);
        return bean;
    }

    // 6、
    @PutMapping("/checkhouses/{id}")
    public void checkright(@PathVariable("id")int id)throws Exception {
        House house = houseService.get(id);
        house.setState("上架");
        houseService.update(house);
    }

    // 管理员-获取所有待审核的房源信息
    @GetMapping("/checkhouses")
    public List<House> checkhouses() throws Exception {
        return houseService.findAllByStateEquals("待审核");
    }

    // 户主-查看某个户主的房源信息
    @GetMapping("/hhouses")
    public List<House> get(HttpSession session) {
        User user = (User)session.getAttribute("user");
        // 根据电话找到户主，再根据户主找到房子集合
        List<House> houses = houseService.findByHouseholder(householderService.findByTel(user.getTel()));
        houseImageService.setFirstHouseImages(houses);
        return houses;
    }

    // 户主-增加房源信息
    @PostMapping("/hhouses")
    public Object add(@RequestBody House bean, HttpSession session) throws Exception {
        // 根据session获取户主
        User user = (User)session.getAttribute("user");
        Householder householder = householderService.findByTel(user.getTel());
        bean.setHouseholder(householder);
        bean.setState("待审核");
        bean.setCreateDate(new Date());
        houseService.add(bean);
        return bean;
    }

    // 户主-上架
    @PutMapping("uphhouses/{id}")
    public void uphhouses(@PathVariable("id") int id) throws Exception {
        House house = houseService.get(id);
        house.setState("上架");
        houseService.update(house);
    }

    // 户主-下架
    @PutMapping("downhhouses/{id}")
    public void downhhouses(@PathVariable("id") int id) throws Exception {
        House house = houseService.get(id);
        house.setState("下架");
        houseService.update(house);
    }
}