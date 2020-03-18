package com.usc.renting.service;

import com.usc.renting.dao.StatisticsDAO;
import com.usc.renting.pojo.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {
    @Autowired
    StatisticsDAO statisticsDAO;

    public Statistics get(Integer id) {
        return statisticsDAO.getOne(id);
    }

    public void update(Statistics statistics) {
        statisticsDAO.save(statistics);
    }
}
