package com.manage.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
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
    private BigDecimal score;//会员积分
    private String phoneNum;//手机号码
    private String creatDate;//注册时间
    private BigDecimal saleRatio;//会员打折率
    private String vipName;//姓名
    private String vipSex;//性别
    private String birthdate;//出生日期
    private String vipCard;//身份证号码

    private String status;//状态，有效，无效
    private String resultCode;//统一处理结果码
    public CustomerVIP() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVipName() {
        return vipName;
    }

    public void setVipName(String vipName) {
        this.vipName = vipName;
    }

    public String getVipSex() {
        return vipSex;
    }

    public void setVipSex(String vipSex) {
        this.vipSex = vipSex;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getVipCard() {
        return vipCard;
    }

    public void setVipCard(String vipCard) {
        this.vipCard = vipCard;
    }

    public BigDecimal getSaleRatio() {
        return saleRatio;
    }

    public void setSaleRatio(BigDecimal saleRatio) {
        this.saleRatio = saleRatio;
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

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
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
