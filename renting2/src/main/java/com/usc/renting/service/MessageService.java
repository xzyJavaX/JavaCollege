package com.usc.renting.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.usc.renting.dao.CommentFirstDAO;
import com.usc.renting.dao.CommentSecondDAO;
import com.usc.renting.pojo.CommentFirst;
import com.usc.renting.pojo.User;
import com.usc.renting.pojo.Message;
import com.usc.renting.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.usc.renting.dao.MessageDAO;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MessageService {
    @Autowired MessageDAO messageDAO;
    @Autowired
    CommentFirstService commentFirstService;
    @Autowired
    CommentSecondDAO commentSecondDAO;
    @Autowired
    CommentFirstDAO commentFirstDAO;

    public void add(Message bean) {
        messageDAO.save(bean);
    }

    public Page4Navigator<Message> list(int start, int size, int navigatePages) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        // 定义一个Pageable对象，参数分别为页面数，页面大小，sort
        Pageable pageable = new PageRequest(start, size,sort);
        // 直接调用findAll方法
        Page pageFromJPA = messageDAO.findAll(pageable);
        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }

    public Message get(Integer id) {
        return messageDAO.findOne(id);
    }

    public void update(Message message) {
        messageDAO.save(message);
    }

    // 获取总评论数前20的信息
    public List<Message> findAllMessageTop20() {
        // 获取所有信息
        List<Message> messages = messageDAO.findAll();
        // 依次遍历每个信息
        for (Message message : messages) {
            int sum = 0;
            List<CommentFirst> commentFirsts = commentFirstService.findAllByMessage(message);
            for (CommentFirst commentFirst : commentFirsts) {
                sum = sum + commentFirst.getComment_count();
            }
            sum = sum + message.getComment_count();
            message.setSum(sum);
        }

        // 匿名类，按sum总数降序排列
        Collections.sort(messages, new Comparator<Message>() {
            @Override
            public int compare(Message o1, Message o2) {
                return o2.getSum() - o1.getSum();
            }
        });

        // 返回前Top20
        if (messages.size() >= 20) {
            for (int i = 0; i < 20; i++){
                messages.get(i).setRank(i+1);
            }
            return messages.subList(0, 20);
        }
        else{
            for (int i = 0; i < messages.size(); i++){
                messages.get(i).setRank(i+1);
            }
            return messages.subList(0, messages.size());
        }
    }

    // 删除该信息以及下面的所有评论
    @Transactional
    public void delete(Integer id) {
        Message message = messageDAO.getOne(id);
        List<CommentFirst> commentFirsts = commentFirstDAO.findAllByMessage(message);
        for (CommentFirst commentFirst : commentFirsts) {
            commentSecondDAO.deleteAllByCommentFirst(commentFirst);
        }
        commentFirstDAO.deleteAllByMessage(message);
        messageDAO.delete(id);
    }

    public List<Message> findByUser(User user) {
        return messageDAO.findAllByUserOrderByIdDesc(user);
    }

    public int countAll() {
        return messageDAO.countByIdNot(0);
    }
}
