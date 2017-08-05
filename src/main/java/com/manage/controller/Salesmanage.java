package com.manage.controller;

import com.manage.bean.Income;
import com.manage.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * 销售管理
 * Created by Administrator on 2017/8/3.
 */
@RestController
public class Salesmanage {
    @Autowired
    private IncomeRepository incomeRepository;


    /**
     * 销售一单成功后，向数据库添加一条销售记录
     * @param receiveMoney 原价
     * @param favorablePrice 优惠价格
     * @param payMoney 实际收费
     * @param payWay 收费方式 1；现金，2：支付宝；3：微信
     * @param saleGoodsDetails 销售商品详情JSON
     */
    @RequestMapping(value = "/salesSuccess", method = RequestMethod.GET)
    public String salesSuccess(@RequestParam String receiveMoney,@RequestParam String favorablePrice,
    @RequestParam String payMoney,@RequestParam String payWay,@RequestParam String saleGoodsDetails){

        //1.向销售记录表添加一条数据

        //2.打印小票

        //3.返回处理结果

        return null;
    }

    /**
     * 销售一单成功后，打印小票
     */
    private void printTick(){

    }

    /**
     * 获取当天收入
     */
    @RequestMapping(value = "/getCurrentDateIncome", method = RequestMethod.GET)
    public Income getCurrentDateIncome(){

        LocalDate todaydate=LocalDate.now();

        return incomeRepository.findByCurrentDate(todaydate.toString());
    }
}
