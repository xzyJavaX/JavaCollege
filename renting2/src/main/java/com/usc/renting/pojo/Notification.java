package com.usc.renting.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "notification")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    private int notifier;
    private int receiver;
    private int fatherid;
    private int childid;
    private String type;
    private Date createtime;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNotifier() {
        return notifier;
    }

    public void setNotifier(int notifier) {
        this.notifier = notifier;
    }

    public int getReceiver() {
        return receiver;
    }

    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }

    public int getFatherid() {
        return fatherid;
    }

    public void setFatherid(int fatherid) {
        this.fatherid = fatherid;
    }

    public int getChildid() {
        return childid;
    }

    public void setChildid(int childid) {
        this.childid = childid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
