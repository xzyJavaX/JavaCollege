package com.usc.renting.service;

import com.usc.renting.dao.CommentFirstDAO;
import com.usc.renting.pojo.CommentFirst;
import com.usc.renting.pojo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentFirstService {
    @Autowired
    CommentFirstDAO commentFirstDAO;

    public void add(CommentFirst commentFirst){
        commentFirstDAO.save(commentFirst);
    }

    public List<CommentFirst> findAllByMessage(Message message) {
        return commentFirstDAO.findAllByMessageOrderByCreatetimeDesc(message);
    }

    public CommentFirst get(Integer id) {
        return commentFirstDAO.getOne(id);
    }

    public void update(CommentFirst commentFirst) {
        commentFirstDAO.save(commentFirst);
    }

    public int countAll() {
        return commentFirstDAO.countByIdNot(0);
    }
}
