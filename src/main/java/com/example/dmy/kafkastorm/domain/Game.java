package com.example.dmy.kafkastorm.domain;

import java.util.Date;

public class Game {
    //id
    private Integer id;
    //游戏人
     private Integer userId;
     //游戏时间
    private Date time;


    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
