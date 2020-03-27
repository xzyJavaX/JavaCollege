package com.usc.renting.web;

import com.usc.renting.pojo.House;
import com.usc.renting.service.HouseImageService;
import com.usc.renting.service.HouseholderService;
import com.usc.renting.service.HouseService;
import com.usc.renting.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
public class HouseController {
    @Autowired HouseService houseService;
    @Autowired HouseholderService householderService;
    @Autowired HouseImageService houseImageService;

    @GetMapping("/householders/{hid}/houses")
    public Page4Navigator<House> list(@PathVariable("hid") int hid, @RequestParam(value = "start", defaultValue = "0") int start,@RequestParam(value = "size", defaultValue = "4") int size) throws Exception {
        start = start<0?0:start;
        Page4Navigator<House> page =houseService.list(hid, start, size,5 );
        houseImageService.setFirstHouseImages(page.getContent());
        return page;
    }


    @GetMapping("/houses/{id}")
    public House get(@PathVariable("id") int id) throws Exception {
        House bean=houseService.get(id);
        return bean;
    }

    @PostMapping("/houses")
    public Object add(@RequestBody House bean) throws Exception {
        bean.setCreateDate(new Date());
        houseService.add(bean);
        return bean;
    }

    @DeleteMapping("/houses/{id}")
    public void delete(@PathVariable("id") int id)  throws Exception {
        houseService.delete(id);
    }

    @PutMapping("/houses")
    public Object update(@RequestBody House bean) throws Exception {
        houseService.update(bean);
        return bean;
    }


}