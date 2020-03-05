package com.usc.tms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.usc.tms.dao.UserDAO;
import com.usc.tms.pojo.User;
import com.usc.tms.util.Page4Navigator;

@Service
public class UserService {

    @Autowired UserDAO userDAO;

    public Page4Navigator<User> list(int start, int size, int navigatePages) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size,sort);
        Page pageFromJPA =userDAO.findAll(pageable);
        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }





//    增加
    public void add(User user) {
        userDAO.save(user);
    }

    public void delete(int id){
        userDAO.delete(id);
    }

    public void update(User user){
        userDAO.save(user);
    }

    public User get(int id){
        return userDAO.getOne(id);
    }

    public User get(String username, String password) {
        return userDAO.getByUsernameAndPassword(username,password);
    }

    public User getByUsername(String username){
        return userDAO.getByUsername(username);
    }
}