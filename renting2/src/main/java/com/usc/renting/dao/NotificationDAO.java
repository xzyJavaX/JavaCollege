package com.usc.renting.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.usc.renting.pojo.Notification;

import java.util.List;

public interface NotificationDAO extends JpaRepository<Notification,Integer> {
    List<Notification> getAllByReceiver(Integer id);

    Page<Notification> findAllByReceiver(Integer id, Pageable pageable);

    Page<Notification> findAllByReceiverAndStatusEquals(Integer id,String status,Pageable pageable);

    int countByReceiverAndStatusEquals(Integer id,String status);

    List<Notification> getAllByReceiverAndStatusEquals(Integer id,String status);

    void deleteAllByReceiver(Integer id);
}
