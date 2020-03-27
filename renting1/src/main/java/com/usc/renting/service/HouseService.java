package com.usc.renting.service;

import com.usc.renting.dao.HouseDAO;
import com.usc.renting.pojo.HouseImage;
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
import java.util.List;


@Service
public class HouseService  {

    @Autowired HouseDAO houseDAO;
    @Autowired HouseholderService householderService;
    @Autowired ReviewService reviewService;
    @Autowired HouseImageService houseImageService;
    public void add(House bean) {
        houseDAO.save(bean);
    }

//    删除房屋的同时删除房屋图片和评论
    @Transactional
    public void delete(int id) {
        House house=houseDAO.getOne(id);
        houseImageService.deleteAll(house);
        reviewService.deleleAll(house);
        houseDAO.delete(id);
    }

    public House get(int id) {
        return houseDAO.findOne(id);
    }

    public void update(House bean) {
        houseDAO.save(bean);
    }

    public Page4Navigator<House> list(int hid, int start, int size,int navigatePages) {
        Householder householder = householderService.get(hid);
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page<House> pageFromJPA =houseDAO.findByHouseholder(householder,pageable);
        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }

    public List<House> list(){
//        根据id降序，findAll()默认按id升序
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        return houseDAO.findAll(sort);
    }

//    接收一个House对象，给该对象设置评论数
    public void setReviewNumber(House house){
//根据house获取评论数
        int reviewCount = reviewService.getCount(house);
//        设置house的评论数
        house.setReviewCount(reviewCount);
    }

//    接收一个House对象集合，依次给每个House对象设置评论数
    public void setReviewNumber(List<House> houses){
        for (House house : houses)
            setReviewNumber(house);
    }

//    根据关键字keyword搜索房源
public List<House> search(String keyword,int start,int size){
    Sort sort = new Sort(Sort.Direction.DESC,"id");
//    new一个Pageable对象，参数分别是当前页数，页面大小，sort
    Pageable pageable = new PageRequest(start,size,sort);
//    调用DAO层方法
    List<House> houses=houseDAO.findByLocationLike("%"+keyword+"%",pageable);

//    如果按位置查询没有结果就按价格查询
    if(houses.size()==0){
        houses=houseDAO.findByPriceLike("%"+keyword+"%",pageable);
    }
    return houses;
}


}