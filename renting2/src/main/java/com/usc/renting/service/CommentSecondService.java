package com.usc.renting.service;

import com.usc.renting.dao.CommentSecondDAO;
import com.usc.renting.pojo.CommentFirst;
import com.usc.renting.pojo.CommentSecond;
import com.usc.renting.pojo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentSecondService {
    @Autowired
    CommentSecondDAO commentSecondDAO;

    public void add(CommentSecond commentSecond) {
        commentSecondDAO.save(commentSecond);
    }

    public List<CommentSecond> findAllByCommentFirst(CommentFirst commentFirst) {
        return commentSecondDAO.findAllByCommentFirstOrderByCreatetimeDesc(commentFirst );
    }

    public CommentSecond get(Integer id) {
        return commentSecondDAO.getOne(id);
    }

    public void update(CommentSecond commentSecond) {
        commentSecondDAO.save(commentSecond);
    }

    public int countAll() {
        return commentSecondDAO.countByIdNot(0);
    }
}
