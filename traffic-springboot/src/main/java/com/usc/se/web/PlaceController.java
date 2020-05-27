package com.usc.se.web;

import com.usc.se.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class PlaceController {
    @Autowired
    PlaceService placeService;

    // 1、获取驻留地的经纬度
    @GetMapping("/resident")
    public ArrayList<HashMap<String, Double[]>> getResident() {
        return placeService.getResident();
    }

    // 2、获取工作地的经纬度
    @GetMapping("/work")
    public ArrayList<HashMap<String, Double[]>> getWork() {
        return placeService.getWork();
    }

    // 3、获取居住地的经纬度
    @GetMapping("/live")
    public ArrayList<HashMap<String, Double[]>> getLive() {
        return placeService.getLive();
    }
}
