package com.usc.se.web;

import com.usc.se.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class StationController {
    @Autowired
    StationService stationService;

    @GetMapping("/station")
    @CrossOrigin
    public ArrayList<Object> getJamByTime() {
        return stationService.getStation();
    }

    @GetMapping("/speed")
    public Double[] getSpeedByName(@RequestParam("name")String name) {
       return stationService.getSpeedByName(name);
    }

}
