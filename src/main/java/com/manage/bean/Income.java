package com.manage.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 当前收入
 * Created by Administrator on 2017/8/4.
 */
@Entity
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;//自增ID
    private String currentDate;//当天日期
    private BigDecimal cashMoney;//现金收入
    private BigDecimal weChatMoney;//微信收入
    private BigDecimal payBabyMoney;//支付宝收入

    public Income() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public BigDecimal getCashMoney() {
        return cashMoney;
    }

    public void setCashMoney(BigDecimal cashMoney) {
        this.cashMoney = cashMoney;
    }

    public BigDecimal getWeChatMoney() {
        return weChatMoney;
    }

    public void setWeChatMoney(BigDecimal weChatMoney) {
        this.weChatMoney = weChatMoney;
    }

    public BigDecimal getPayBabyMoney() {
        return payBabyMoney;
    }

    public void setPayBabyMoney(BigDecimal payBabyMoney) {
        this.payBabyMoney = payBabyMoney;
    }
}
