package com.usc.se.web;

import com.usc.se.service.TrafficJamService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class TrafficController {
    @Autowired
    TrafficJamService trafficJamService;

    /**
     *  返回前一个小时的交通拥挤情况
     */
    @GetMapping("/jam_prior")
    @CrossOrigin
    public ArrayList<Object> trafficJamPrior() {
        return trafficJamService.getPrior();
    }

    /**
     * 返回下一个小时的交通拥挤情况
     */
    @GetMapping("/jam_next")
    @CrossOrigin
    public ArrayList<Object> trafficJamNext() {
        return trafficJamService.getNext();
    }

    /**
     * 返回0-1点时的数据
     */
    @GetMapping("/jam_zero")
    @CrossOrigin
    public ArrayList<Object> trafficJam() {
        return trafficJamService.get();
    }

    /**
     * 根据时间返回数据
     */
    @GetMapping("/jam")
    @CrossOrigin
    public ArrayList<Object> getJamByTime(@RequestParam("time") Integer time) {
        return trafficJamService.getByTime(time);
    }
}
