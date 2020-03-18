package com.usc.renting.dao;

import java.util.List;

import com.usc.renting.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.usc.renting.pojo.House;
import com.usc.renting.pojo.Review;

public interface ReviewDAO extends JpaRepository<Review, Integer> {
    // 接收房子对象，返回对应的评论集合
    List<Review> findByHouseOrderByIdDesc(House house);

    // 接收House对象，获取评论数
    int countByHouse(House house);

    // 接收house，删除对应的评论
    void deleteAllByHouse(House house);

    int countByUserAndHouse(User user, House house);
}
