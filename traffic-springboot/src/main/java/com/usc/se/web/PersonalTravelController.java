package com.usc.se.web;

import com.usc.se.service.PersonalTravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class PersonalTravelController {
    @Autowired
    PersonalTravelService personalTravelService;

    // 返回个人出行的全部情况
    @GetMapping("/personalAll")
    public ArrayList<HashMap<String,Object>> getAll() {
        return personalTravelService.getAll();
    }

    // 返回夜行的全部情况
    @GetMapping("/personalNight")
    public ArrayList<HashMap<String,Object>> getNight() {
        return personalTravelService.getNight();
    }
}
