package com.usc.tms.dao;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.usc.tms.pojo.Useinfo;

import javax.jws.soap.SOAPBinding;
import java.util.List;


public interface UseinfoDAO extends JpaRepository<Useinfo,Integer>{

    List<Useinfo> getAllByFid(String fid);

    List<Useinfo> getAllByFid(String fid,Sort sort);

}