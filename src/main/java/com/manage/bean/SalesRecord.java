package com.manage.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 销售记录
 * Created by Administrator on 2017/8/3.
 */
@Entity
public class SalesRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;//自增ID
    private long salesId;//销售编号
    private String customerID;//顾客编号
    private BigDecimal originPrice;//本次销售员商品价格合计
    private int saleCount;//本次销售商品件数
    private BigDecimal favorablePrice;//本次销售优惠价格
    //private BigDecimal receiveMoney;//本次销售原价格
    private BigDecimal payMoney;//本次销售实收价格
    private String payWay;//支付方式：1；现金，2：支付宝；3：微信
    private String salsGoodDetails;//销售商品详情的JSON数据
private String cashier;//收银员
    private String creatDate;//销售时间

    public SalesRecord() {
    }

    public String getCashier() {
        return cashier;
    }

    public void setCashier(String cashier) {
        this.cashier = cashier;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSalesId() {
        return salesId;
    }

    public void setSalesId(long salesId) {
        this.salesId = salesId;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public BigDecimal getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(BigDecimal originPrice) {
        this.originPrice = originPrice;
    }

    public int getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(int saleCount) {
        this.saleCount = saleCount;
    }

    public BigDecimal getFavorablePrice() {
        return favorablePrice;
    }

    public void setFavorablePrice(BigDecimal favorablePrice) {
        this.favorablePrice = favorablePrice;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getSalsGoodDetails() {
        return salsGoodDetails;
    }

    public void setSalsGoodDetails(String salsGoodDetails) {
        this.salsGoodDetails = salsGoodDetails;
    }

    public String getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(String creatDate) {
        this.creatDate = creatDate;
    }
}
