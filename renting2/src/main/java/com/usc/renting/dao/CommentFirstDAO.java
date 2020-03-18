package com.usc.renting.dao;

import com.usc.renting.pojo.CommentFirst;
import com.usc.renting.pojo.Message;
import com.usc.renting.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentFirstDAO extends JpaRepository<CommentFirst, Integer> {
    List<CommentFirst> findAllByMessageOrderByCreatetimeDesc(Message message);

    int countByIdNot(int id);

    int countByUser(User user);

    void deleteAllByMessage(Message message);

    List<CommentFirst> findAllByMessage(Message message);
}
