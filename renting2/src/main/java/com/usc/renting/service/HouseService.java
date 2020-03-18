package com.usc.renting.service;

import com.usc.renting.dao.CollectionDAO;
import com.usc.renting.dao.HouseDAO;
import com.usc.renting.dao.ScanDAO;
import com.usc.renting.pojo.Householder;
import com.usc.renting.pojo.House;
import com.usc.renting.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class HouseService {
    @Autowired
    HouseDAO houseDAO;
    @Autowired
    HouseholderService householderService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    HouseImageService houseImageService;
    @Autowired
    CollectionDAO collectionDAO;
    @Autowired
    ScanDAO scanDAO;

    public void add(House bean) {
        houseDAO.save(bean);
    }

    // 联级删除，删除房屋的同时删除房屋图片和评论，收藏，浏览
    @Transactional
    public void delete(int id) {
        House house=houseDAO.getOne(id);
        // 删图片
        houseImageService.deleteAll(house);
        // 删评论
        reviewService.deleleAll(house);
        // 删收藏
        collectionDAO.deleteAllByHouse(house);
        // 删浏览
        scanDAO.deleteAllByHouse(house);
        // 删房子
        houseDAO.delete(id);
    }

    // 根据id获取House
    public House get(int id) {
        return houseDAO.findOne(id);
    }

    // 更新House
    public void update(House bean) {
        houseDAO.save(bean);
    }

    // 获取每个户主对应的房源信息
    public Page4Navigator<House> list(int hid, int start, int size, int navigatePages) {
        Householder householder = householderService.get(hid);
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page<House> pageFromJPA = houseDAO.findByHouseholder(householder, pageable);
        return new Page4Navigator<>(pageFromJPA, navigatePages);
    }

    // 获取所有房源信息
    public List<House> list() {
        // 根据id降序，findAll()默认按id升序
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return houseDAO.findAll(sort);
    }

    // 接收一个House对象，给该对象设置评论数
    public void setReviewNumber(House house) {
        // 根据house获取评论数
        int reviewCount = reviewService.getCount(house);
        // 设置house的评论数
        house.setReviewCount(reviewCount);
    }

    // 接收一个House对象集合，依次给每个House对象设置评论数
    public void setReviewNumber(List<House> houses) {
        for (House house : houses)
            setReviewNumber(house);
    }

    public List<House> findByHouseholder(Householder householder) {
        return houseDAO.findByHouseholder(householder);
    }

    // 首页的房源信息的三种排序
    public List<House> findAllByStateEquals(String state) {
        List<House> houses = houseDAO.findAllByStateEquals(state);
        Collections.shuffle(houses);
        return houses;
    }

    public List<House> findAllOrderByCol(String state) {
        return houseDAO.findAllByStateEqualsOrderByColnumDesc(state);
    }

    public List<House> findAllOrderByTime(String state) {
        return houseDAO.findAllByStateEqualsOrderByCreateDateDesc(state);
    }

    public List<House> findAllOrderByScan() {
        return houseDAO.findAllByStateEqualsOrderByScannumDesc("上架");
    }

    // 搜索页的房源信息的三种排序
    public List<House> search(String keyword) {
        // 调用DAO层方法
        List<House> houses=houseDAO.findAllByStateEqualsAndLocationLike("上架", "%" + keyword + "%");
        Collections.shuffle(houses);
        return houses;
    }

    public List<House> findAllByKeyWordOrderByCol(String keyword) {
        return houseDAO.findAllByStateAndLocationLikeOrderByColnumDesc("上架", "%"+keyword+"%");
    }

    public List<House> findAllByKeyWordOrderByTime(String keyword) {
        return houseDAO.findAllByStateAndLocationLikeOrderByCreateDateDesc("上架", "%"+keyword+"%");
    }

    public List<House> findAllByKeyWordOrderByScan(String keyword) {
        return houseDAO.findAllByStateAndLocationLikeOrderByScannumDesc("上架", "%"+keyword+"%");
    }

    public List<House> findAll() {
        return houseDAO.findAll();
    }

    // 总房源数
    public int countAll() {
        return houseDAO.countByIdNot(0);
    }

    // 总房间数
    public int countAll1() {
        int sum = 0;
        List<House> houses = houseDAO.findAll();
        for (House house : houses) {
            sum = sum + house.getNum();
        }
        return sum;
    }
}