package com.usc.renting.service;

import com.usc.renting.dao.HouseImageDAO;
import com.usc.renting.pojo.House;
import com.usc.renting.pojo.HouseImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseImageService {

    public static final String type_single = "single";
    public static final String type_detail = "detail";

    @Autowired HouseImageDAO houseImageDAO;
    @Autowired HouseService houseService;

    public void add(HouseImage bean) {
        houseImageDAO.save(bean);
    }

    public void delete(int id) {
        houseImageDAO.delete(id);
    }

    public HouseImage get(int id) {
        return houseImageDAO.findOne(id);
    }

    // 接收一个House对象，获取"单个图片"HouseImage集合
    public List<HouseImage> listSingleHouseImages(House house) {
        return houseImageDAO.findByHouseAndTypeOrderByIdDesc(house, type_single);
    }

    // 接收一个House对象，获取"详情图片"HouseImage集合
    public List<HouseImage> listDetailHouseImages(House house) {
        return houseImageDAO.findByHouseAndTypeOrderByIdDesc(house, type_detail);
    }

    // 接收一个House对象
    public void setFirstHouseImage(House house) {
        // 调用listSingleHouseImages方法，获取house对应的house"单个图片"集合
        List<HouseImage> singleImages = listSingleHouseImages(house);
        // 如果单个图片集合不为空
        if(!singleImages.isEmpty()) {
            // 把singleImages的第一个对象加到House对象里面
            house.setFirstHouseImage(singleImages.get(0));
        }
        else {
            // 这样做是考虑到产品还没有来得及设置图片，但是在订单后台管理里查看订单项的对应产品图片。
            house.setFirstHouseImage(new HouseImage());
        }
    }

    // 接收一个house集合，依次对每个house对象设置第一张图片
    public void setFirstHouseImages(List<House> houses) {
        for (House house : houses)
            setFirstHouseImage(house);
    }

    public void deleteAll(House house){
        houseImageDAO.deleteAllByHouse(house);
    }
}