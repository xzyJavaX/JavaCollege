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

import com.usc.tms.dao.UseinfoDAO;
import com.usc.tms.pojo.Useinfo;
import com.usc.tms.util.Page4Navigator;

import java.util.List;
@Service
public class UseinfoService {

    @Autowired UseinfoDAO useinfoDAO;


    public Page4Navigator<Useinfo> list(int start, int size, int navigatePages) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size,sort);
        Page pageFromJPA =useinfoDAO.findAll(pageable);
        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }





    //    增加
    public void add(Useinfo useinfo) {
        useinfoDAO.save(useinfo);
    }

    public void delete(int id){
        useinfoDAO.delete(id);
    }

    public void update(Useinfo useinfo){
        useinfoDAO.save(useinfo);
    }

    public Useinfo get(int id){
        return useinfoDAO.getOne(id);
    }


    public List<Useinfo> getAllbyFid(String fid){
        return useinfoDAO.getAllByFid(fid);
    }



    public List<Useinfo> getAll(String fid){
        Sort sort = new Sort(Sort.Direction.DESC,"outdate");
        return useinfoDAO.getAllByFid(fid);
    }


}