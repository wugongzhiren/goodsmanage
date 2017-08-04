package com.manage.bean;

import java.math.BigDecimal;

/**
 * 商品信息
 * Created by Administrator on 2017/8/3.
 */
public class Goods {
    private long id;//自增ID
    private long goodsId;//商品在库ID，对应条形码的4-12位
    private long zxingCode;//条形码code
    private int sumCount;//商品在库数量
    private BigDecimal price;//商品单价
}
