package com.usc.renting.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usc.renting.pojo.House;
import com.usc.renting.pojo.HouseImage;

public interface HouseImageDAO extends JpaRepository<HouseImage, Integer>{
    // 根据房子和图片类型搜索房子图片集合  OrderByIdDesc代表按Id降序
    List<HouseImage> findByHouseAndTypeOrderByIdDesc(House house, String type);

    // 根据房子删除所有图片
    void deleteAllByHouse(House house);
}