package com.usc.renting.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usc.renting.pojo.Householder;

import java.util.List;

public interface HouseholderDAO extends JpaRepository<Householder,Integer>
{

    List<Householder> findByNameLike(String name);
}