package com.usc.renting.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usc.renting.pojo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserDAO extends JpaRepository<User, Integer>{
    // 根据tel查询对应的User，用于注册
    User findByTel(String tel);

    User findByTelAndPassword(String tel,String password);

    Page<User> findAllByPositionEquals(Pageable pageable,String position);

    int countByIdNot(int id);
}