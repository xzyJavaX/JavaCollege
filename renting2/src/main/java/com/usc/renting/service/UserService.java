package com.usc.renting.service;

import com.usc.renting.dao.CommentFirstDAO;
import com.usc.renting.dao.CommentSecondDAO;
import com.usc.renting.dao.MessageDAO;
import com.usc.renting.pojo.CommentSecond;
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

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDAO userDAO;
    @Autowired
    MessageDAO messageDAO;
    @Autowired
    CommentFirstDAO commentFirstDAO;
    @Autowired
    CommentSecondDAO commentSecondDAO;

    // 查询用户所有分页信息
    public Page4Navigator<User> list(int start, int size, int navigatePages) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page pageFromJPA =userDAO.findAll(pageable);
        return new Page4Navigator<>(pageFromJPA, navigatePages);
    }

    // 根据用户等级查询所有分页信息
    public Page4Navigator<User> list1(int start, int size, int navigatePages,String position) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page pageFromJPA = userDAO.findAllByPositionEquals(pageable, position);
        return new Page4Navigator<>(pageFromJPA, navigatePages);
    }

    // 用于注册，根据tel判断是否存在该user
    public boolean isExist(String tel) {
        User user = userDAO.findByTel(tel);
        return null != user;
    }

    public void add(User user) {
        userDAO.save(user);
    }

    // 登录
    public User get(String tel, String password) {
        return userDAO.findByTelAndPassword(tel, password);
    }

    public void update(User user) {
        userDAO.save(user);
    }

    public User get(int id) {
        return userDAO.getOne(id);
    }

    public int countAll() {
        return userDAO.countByIdNot(0);
    }

    public int countChatroom() {
        List<User> users = userDAO.findAll();
        int sum = 0;
        for (User user : users) {
            if (messageDAO.countByUser(user)!=0||commentFirstDAO.countByUser(user)!=0||commentSecondDAO.countByUser(user)!=0)
                sum++;
        }
        return sum;
    }
}