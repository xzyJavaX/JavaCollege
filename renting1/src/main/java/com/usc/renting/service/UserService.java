package com.usc.renting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.usc.renting.dao.UserDAO;
import com.usc.renting.pojo.User;
import com.usc.renting.util.Page4Navigator;

@Service
public class UserService {

    @Autowired UserDAO userDAO;

    public Page4Navigator<User> list(int start, int size, int navigatePages) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size,sort);
        Page pageFromJPA =userDAO.findAll(pageable);
        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }

//    根据name判断是否存在该user，调用getByName方法
    public boolean isExist(String name) {
        User user = getByName(name);
        return null!=user;

    }

//    调用DAO层的findByName方法
    public User getByName(String name) {
        return userDAO.findByName(name);
    }


    public void add(User user) {
        userDAO.save(user);
    }

    public User get(String name, String password) {
        return userDAO.getByNameAndPassword(name,password);
    }

    public void update(User user){
        userDAO.save(user);
    }

    public User get(int id){
        return userDAO.getOne(id);
    }
}