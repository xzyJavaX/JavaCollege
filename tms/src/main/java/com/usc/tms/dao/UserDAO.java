package com.usc.tms.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usc.tms.pojo.User;

public interface UserDAO extends JpaRepository<User,Integer>{
    User getByUsernameAndPassword(String username,String password);
    User getByUsername(String username);
}