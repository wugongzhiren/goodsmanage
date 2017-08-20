package com.manage.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * 商品信息
 * Created by Administrator on 2017/8/3.
 */
@Entity
public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;//自增ID
    private String zxingCode;//条形码code
    private BigDecimal price;//商品单价，原价
    private BigDecimal salePrice;//当前商品折扣价
    private String goodsType;//商品分类
    private String goodsName;//商品名称
    private int goodsCount;//商品在库数量
    private String goodsVersion;//商品型号
    private String resultCode;

    private int addCount;//非第一次入库用于商品增加进货数量

    private String goodsDescs;//商品说明，如今日特价9.9等

    private String sumPrice;//辅助字段

    private String instoreDate;//入库时间
    public Goods() {
    }

    public String getInstoreDate() {
        return instoreDate;
    }

    public void setInstoreDate(String instoreDate) {
        this.instoreDate = instoreDate;
    }

    public String getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(String sumPrice) {
        this.sumPrice = sumPrice;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public String getGoodsDescs() {
        return goodsDescs;
    }

    public void setGoodsDescs(String goodsDescs) {
        this.goodsDescs = goodsDescs;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getZxingCode() {
        return zxingCode;
    }

    public void setZxingCode(String zxingCode) {
        this.zxingCode = zxingCode;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public int getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }

    public String getGoodsVersion() {
        return goodsVersion;
    }

    public void setGoodsVersion(String goodsVersion) {
        this.goodsVersion = goodsVersion;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public int getAddCount() {
        return addCount;
    }

    public void setAddCount(int addCount) {
        this.addCount = addCount;
    }
}
