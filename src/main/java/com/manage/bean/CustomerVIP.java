package com.manage.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * 会员
 * Created by Administrator on 2017/8/4.
 */
@Entity
public class CustomerVIP {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;//自增ID
    private String vipID;//会员ID：可能是微信的二维码
    private long score;//会员积分
    private String phoneNum;//手机号码
    private String creatDate;//注册时间

    private String resultCode;//统一处理结果码
    public CustomerVIP() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVipID() {
        return vipID;
    }

    public void setVipID(String vipID) {
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

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
}
