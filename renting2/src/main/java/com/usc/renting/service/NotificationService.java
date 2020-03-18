package com.usc.renting.service;

import com.usc.renting.dao.NotificationDAO;
import com.usc.renting.pojo.House;
import com.usc.renting.pojo.Householder;
import com.usc.renting.pojo.Notification;
import com.usc.renting.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    NotificationDAO notificationDAO;

    public void addCommentFirst(Integer nid, Integer rid, Integer fid, Integer cid) {
        Notification notification = new Notification();
        notification.setNotifier(nid);
        notification.setReceiver(rid);
        notification.setFatherid(fid);
        notification.setChildid(cid);
        notification.setType("评论");
        notification.setCreatetime(new Date());
        notification.setStatus("未读");
        notificationDAO.save(notification);
    }

    public void addCommentSecond(Integer nid, Integer rid, Integer fid, Integer cid){
        Notification notification = new Notification();
        notification.setNotifier(nid);
        notification.setReceiver(rid);
        notification.setFatherid(fid);
        notification.setChildid(cid);
        notification.setType("回复");
        notification.setCreatetime(new Date());
        notification.setStatus("未读");
        notificationDAO.save(notification);
    }

    @Transactional
    public void deleteAllNotifi(Integer id){
        notificationDAO.deleteAllByReceiver(id);
    }

    public void readAllNotifi(Integer id){
        List<Notification> notifications = notificationDAO.getAllByReceiverAndStatusEquals(id, "未读");
        for (Notification notification : notifications){
            notification.setStatus("已读");
            notificationDAO.save(notification);
        }
    }

    public Page4Navigator<Notification> list(Integer id, String status, int start, int size, int navigatePages) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page<Notification> pageFromJPA;
        if (status.equals("all")) {
            pageFromJPA = notificationDAO.findAllByReceiver(id, pageable);
        }
        else{
            pageFromJPA = notificationDAO.findAllByReceiverAndStatusEquals(id, status, pageable);
        }
        return new Page4Navigator<>(pageFromJPA, navigatePages);
    }

    public int countByReceiverAndStatusEquals(Integer id, String status) {
        return notificationDAO.countByReceiverAndStatusEquals(id, status);
    }

    public void delete(Integer id) {
        notificationDAO.delete(id);
    }

    public Notification get(Integer id) {
        return notificationDAO.getOne(id);
    }

    public void update(Notification notification) {
        notificationDAO.save(notification);
    }
}
