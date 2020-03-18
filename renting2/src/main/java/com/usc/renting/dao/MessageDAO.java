package com.usc.renting.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.usc.renting.pojo.Message;
import com.usc.renting.pojo.User;

import java.util.List;

public interface MessageDAO extends JpaRepository<Message, Integer> {
    List<Message> findAll();

    List<Message> findAllByUserOrderByIdDesc(User user);

    int countByUser(User user);

    int countByIdNot(int id);
}
