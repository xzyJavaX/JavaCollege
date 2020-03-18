package com.usc.renting.dao;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.usc.renting.pojo.House;
import com.usc.renting.pojo.Householder;

public interface HouseDAO extends JpaRepository<House, Integer>{

    // 接收HouseHolder对象和Pageable对象，获取Page集合
    Page<House> findByHouseholder(Householder householder, Pageable pageable);

    // List<House> findByHouseholder(Householder householder);
    List<House> findAllByStateEqualsAndLocationLike(String state, String keyword);

    // 根据户主，找到房子集合，用户户主-后台管理
    List<House> findByHouseholder(Householder householder);

    // 找到状态="上架"的房子
    List<House> findAllByStateEquals(String state);

    // 找到状态="上架"的房子，按收藏数排序
    List<House> findAllByStateEqualsOrderByColnumDesc(String state);

    // 找到状态="上架"的房子，按创建时间降序排列，
    List<House> findAllByStateEqualsOrderByCreateDateDesc(String state);

    // 找到状态="上架"的房子，按浏览次数降序排列
    List<House> findAllByStateEqualsOrderByScannumDesc(String state);

    List<House> findAllByStateAndLocationLikeOrderByColnumDesc(String state, String keyword);

    List<House> findAllByStateAndLocationLikeOrderByCreateDateDesc(String state, String keyword);

    List<House> findAllByStateAndLocationLikeOrderByScannumDesc(String state, String keyword);

    int countByIdNot(int id);
}