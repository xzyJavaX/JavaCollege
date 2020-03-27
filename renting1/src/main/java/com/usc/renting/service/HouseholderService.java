package com.usc.renting.service;

import java.util.List;

import com.usc.renting.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.usc.renting.dao.HouseholderDAO;
import com.usc.renting.pojo.Householder;

@Service
public class HouseholderService {
    @Autowired HouseholderDAO householderDAO;

    public Page4Navigator<Householder> list(int start, int size, int navigatePages) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
//        定义一个Pageable对象，参数分别为页面数，页面大小，sort
        Pageable pageable = new PageRequest(start, size,sort);
//        直接调用findAll方法
        Page pageFromJPA =householderDAO.findAll(pageable);
        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }

    public void add(Householder bean) {
        householderDAO.save(bean);
    }

    public void delete(int id) {
        householderDAO.delete(id);
    }

    public Householder get(int id) {
        Householder c= householderDAO.findOne(id);
        return c;
    }

    public void update(Householder bean) {
        householderDAO.save(bean);
    }

    public List<Householder> search(String name){
        return householderDAO.findByNameLike(name);
    }
}