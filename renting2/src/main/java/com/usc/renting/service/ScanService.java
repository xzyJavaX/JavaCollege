package com.usc.renting.service;

import com.usc.renting.dao.ScanDAO;
import com.usc.renting.pojo.House;
import com.usc.renting.pojo.Scan;
import com.usc.renting.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ScanService {
    @Autowired
    ScanDAO scanDAO;

    public void add(User user, House house) {
        Scan scan = new Scan();
        scan.setUser(user);
        scan.setHouse(house);
        scanDAO.save(scan);
    }

    public List<Scan> getScan(User user) {
        return scanDAO.getAllByUserOrderById(user);
    }

    public void delete(Scan scan) {
        scanDAO.delete(scan);
    }

    public int countScan() {
        return scanDAO.countByIdIsNot(0);
    }
}
