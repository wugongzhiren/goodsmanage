package com.manage.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * 销售记录详细，每条销售记录对应销售商品的详细
 * Created by Administrator on 2017/8/3.
 */
@Entity
public class SalesGoodDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;//自增ID
    private long saleid;//销售编号
    private String goodsID;//条形码
    private String goodsType;//商品分类
    private String goodsName;//商品名称
    private BigDecimal goodSprice;//商品单价
    private int goodsCount;//购买商品数量

    public SalesGoodDetails() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSaleid() {
        return saleid;
    }

    public void setSaleid(long saleid) {
        this.saleid = saleid;
    }

    public String getGoodsID() {
        return goodsID;
    }

    public void setGoodsID(String goodsID) {
        this.goodsID = goodsID;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public BigDecimal getGoodSprice() {
        return goodSprice;
    }

    public void setGoodSprice(BigDecimal goodSprice) {
        this.goodSprice = goodSprice;
    }

    public int getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }
}
