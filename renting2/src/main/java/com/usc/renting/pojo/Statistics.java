package com.usc.renting.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "statistics")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class Statistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    private int home;
    private int chatroom;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHome() {
        return home;
    }

    public void setHome(int home) {
        this.home = home;
    }

    public int getChatroom() {
        return chatroom;
    }

    public void setChatroom(int chatroom) {
        this.chatroom = chatroom;
    }
}
