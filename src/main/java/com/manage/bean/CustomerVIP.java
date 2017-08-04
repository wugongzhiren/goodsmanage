package com.manage.bean;

import java.util.Date;

/**
 * 会员
 * Created by Administrator on 2017/8/4.
 */
public class CustomerVIP {
    private long id;//自增ID
    private long vipID;//会员ID
    private long score;//会员积分
    private String creatDate;//注册时间

    public CustomerVIP() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getVipID() {
        return vipID;
    }

    public void setVipID(long vipID) {
        this.vipID = vipID;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public String getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(String creatDate) {
        this.creatDate = creatDate;
    }
}
