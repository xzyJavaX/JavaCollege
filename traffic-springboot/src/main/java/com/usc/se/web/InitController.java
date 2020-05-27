package com.usc.se.web;

import com.usc.se.service.PersonalTravelService;
import com.usc.se.service.PlaceService;
import com.usc.se.service.StationService;
import com.usc.se.service.TrafficJamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 初始化接口
 */
@RestController
public class InitController {
    @Autowired
    PersonalTravelService personalTravelService;

    @Autowired
    PlaceService placeService;

    @Autowired
    StationService stationService;

    @Autowired
    TrafficJamService trafficJamService;

    @GetMapping("/init")
    public void init() {
        // 初始化接口1
        trafficJamService.init();

        // 初始化接口2
        stationService.init();

        // 初始化接口3
        placeService.initResident();

        // 初始化接口4
        placeService.initWork();

        // 初始化接口5
        placeService.initLive();

        // 初始化接口6
        personalTravelService.initAll();

        // 初始化接口7
        personalTravelService.initNight();

        // 初始化接口8
        stationService.initV();
    }
}
