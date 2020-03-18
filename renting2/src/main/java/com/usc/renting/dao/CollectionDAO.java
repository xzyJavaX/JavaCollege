package com.usc.renting.dao;

import com.usc.renting.pojo.Collection;
import com.usc.renting.pojo.House;
import com.usc.renting.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollectionDAO extends JpaRepository<Collection, Integer> {
    Collection getByUserAndHouse(User user, House house);

    List<Collection> getAllByUser(User user);

    void deleteAllByHouse(House house);
}
