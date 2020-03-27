package com.usc.renting.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.usc.renting.pojo.House;
import com.usc.renting.pojo.Householder;

public interface HouseDAO extends JpaRepository<House,Integer>{

//    接收HouseHolder对象和Pageable对象，获取Page集合
    Page<House> findByHouseholder(Householder householder, Pageable pageable);
//    List<House> findByHouseholder(Householder householder);


    List<House> findByLocationLike(String keyword,Pageable pageable);

    List<House> findByPriceLike(String keyword,Pageable pageable);
}