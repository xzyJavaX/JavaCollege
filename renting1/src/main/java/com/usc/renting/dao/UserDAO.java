package com.usc.renting.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usc.renting.pojo.User;

public interface UserDAO extends JpaRepository<User,Integer>{
//    根据name查询对应的User
    User findByName(String name);

    User getByNameAndPassword(String name,String password);


}