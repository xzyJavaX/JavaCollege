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

import com.usc.tms.dao.FixtureDAO;
import com.usc.tms.pojo.Fixture;
import com.usc.tms.util.Page4Navigator;

import java.util.List;

@Service
public class FixtureService {

    @Autowired FixtureDAO fixtureDAO;

    public Page4Navigator<Fixture> list(int start, int size, int navigatePages) {
        Sort sort = new Sort(Sort.Direction.DESC, "regdate");
        Pageable pageable = new PageRequest(start, size,sort);
        Page pageFromJPA =fixtureDAO.findAll(pageable);
        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }





    //    增加
    public void add(Fixture fixture) {
        fixtureDAO.save(fixture);
    }

    public void delete(String id){
        fixtureDAO.delete(id);
    }

    public void update(Fixture fixture){
        fixtureDAO.save(fixture);
    }

    public Fixture get(String id){
        return fixtureDAO.getOne(id);
    }

    public Fixture getByid(String id){
        return fixtureDAO.getById(id);
    }


    public List<Fixture> getAll(){
        Sort sort = new Sort(Sort.Direction.ASC,"usedCount");
        return fixtureDAO.findAll(sort);
    }




}