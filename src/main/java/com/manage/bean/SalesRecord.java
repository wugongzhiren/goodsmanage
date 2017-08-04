package com.manage.bean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 销售记录
 * Created by Administrator on 2017/8/3.
 */
public class SalesRecord {

    private long salesId;//销售编号
    private long customerID;//顾客编号
    private BigDecimal originPrice;//本次销售员商品原价
    private int saleCount;//本次销售商品件数
    private BigDecimal favorablePrice;//本次销售优惠价格
    private BigDecimal receiveMoney;//本次销售应收价格
    private BigDecimal payMoney;//本次销售实收价格
    private int payWay;//支付方式：1；现金，2：支付宝；3：微信

    private Date creatDate;//销售时间
}
