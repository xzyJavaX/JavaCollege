package com.usc.renting.service;

import java.util.List;

import com.usc.renting.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usc.renting.dao.ReviewDAO;
import com.usc.renting.pojo.House;
import com.usc.renting.pojo.Review;

@Service
public class ReviewService {
    @Autowired ReviewDAO reviewDAO;
    @Autowired HouseService houseService;

    // 接收一个House对象，获取评论集合
    public List<Review> list(House house){
        List<Review> result = reviewDAO.findByHouseOrderByIdDesc(house);
        return result;
    }

    // 接收一个House对象，获取评论数
    public int getCount(House house) {
        return reviewDAO.countByHouse(house);
    }

    public void add(Review review) {
        reviewDAO.save(review);
    }

    public void deleleAll(House house) {
        reviewDAO.deleteAllByHouse(house);
    }

    // 接收一个reviews，把前端用不上的数据置为null
    public List<Review> getReviews(List<Review> reviews) {
        for (Review review : reviews) {
            review.setHouse(null);
            // 设置reivew对象中的user对象name为null
            review.user.setName(null);
        }
        return reviews;
    }

    public int countByUserAndHouse(User user, House house){
        return reviewDAO.countByUserAndHouse(user, house);
    }
}
