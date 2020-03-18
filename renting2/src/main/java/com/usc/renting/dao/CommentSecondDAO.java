package com.usc.renting.dao;

import com.usc.renting.pojo.CommentFirst;
import com.usc.renting.pojo.CommentSecond;
import com.usc.renting.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentSecondDAO extends JpaRepository<CommentSecond, Integer> {
    List<CommentSecond> findAllByCommentFirstOrderByCreatetimeDesc(CommentFirst commentFirst);

    int countByIdNot(int id);

    int countByUser(User user);

    void deleteAllByCommentFirst(CommentFirst commentFirst);
}
