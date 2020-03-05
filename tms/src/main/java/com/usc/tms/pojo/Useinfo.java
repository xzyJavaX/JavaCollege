package com.usc.tms.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@Entity
@Table(name = "useinfo")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })

public class Useinfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    String shift;
    String fid;
    String location;
    Date outdate;
    String writename;
    String uselocation;

    public String getUselocation() {
        return uselocation;
    }

    public void setUselocation(String uselocation) {
        this.uselocation = uselocation;
    }

    public String getWritename() {
        return writename;
    }

    public void setWritename(String writename) {
        this.writename = writename;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getOutdate() {
        return outdate;
    }

    public void setOutdate(Date outdate) {
        this.outdate = outdate;
    }

    public String getUsename() {
        return usename;
    }

    public void setUsename(String usename) {
        this.usename = usename;
    }

    public Date getIndate() {
        return indate;
    }

    public void setIndate(Date indate) {
        this.indate = indate;
    }

    public String getRetname() {
        return retname;
    }

    public void setRetname(String retname) {
        this.retname = retname;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    String usename;
    Date indate;
    String retname;
    String remark;
}
