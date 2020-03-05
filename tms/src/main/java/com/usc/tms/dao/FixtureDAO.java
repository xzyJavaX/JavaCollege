package com.usc.tms.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usc.tms.pojo.Fixture;


public interface FixtureDAO extends JpaRepository<Fixture,String>{

    Fixture getById(String id);


}