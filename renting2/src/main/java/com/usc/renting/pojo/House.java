package com.usc.renting.pojo;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "house")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer"})
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @ManyToOne
    @JoinColumn(name = "hid")
    private Householder householder;

    private String location;
    private String price;
    private String remark;
    private int num;
    private Date createDate;
    private String state;
    private int colnum;
    private int scannum;

    @Transient
    private HouseImage firstHouseImage;
    @Transient
    private List<HouseImage> houseSingleImages;
    @Transient
    private List<HouseImage> houseDetailImages;
    @Transient
    private int reviewCount;

    public int getScannum() {
        return scannum;
    }

    public void setScannum(int scannum) {
        this.scannum = scannum;
    }

    public int getColnum() {
        return colnum;
    }

    public void setColnum(int colnum) {
        this.colnum = colnum;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public List<HouseImage> getHouseSingleImages() {
        return houseSingleImages;
    }

    public void setHouseSingleImages(List<HouseImage> houseSingleImages) {
        this.houseSingleImages = houseSingleImages;
    }

    public List<HouseImage> getHouseDetailImages() {
        return houseDetailImages;
    }

    public void setHouseDetailImages(List<HouseImage> houseDetailImages) {
        this.houseDetailImages = houseDetailImages;
    }

    public HouseImage getFirstHouseImage() {
        return firstHouseImage;
    }

    public void setFirstHouseImage(HouseImage firstHouseImage) {
        this.firstHouseImage = firstHouseImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Householder getHouseholder() {
        return householder;
    }

    public void setHouseholder(Householder householder) {
        this.householder = householder;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}