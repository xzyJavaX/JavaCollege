package com.usc.renting.service;

import com.usc.renting.dao.CollectionDAO;

import com.usc.renting.pojo.Collection;
import com.usc.renting.pojo.House;
import com.usc.renting.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionService {
    @Autowired
    CollectionDAO collectionDAO;

    public Collection getByUserAndHouse(User user, House house) {
        return collectionDAO.getByUserAndHouse(user, house);
    }

    public void add(User user, House house) {
        Collection collection = new Collection();
        collection.setHouse(house);
        collection.setUser(user);
        collectionDAO.save(collection);
    }

    public List<Collection> getAllByUser(User user) {
        return collectionDAO.getAllByUser(user);
    }

    public void delete(Collection collection) {
        collectionDAO.delete(collection);
    }
}
