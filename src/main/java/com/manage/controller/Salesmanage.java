package com.manage.controller;

import com.manage.bean.Income;
import com.manage.repository.IncomeRepository;
import com.manage.zxing.ZxingEAN13EncoderHandler;
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
     *生成Zxing条码
     * @return
     */
    @RequestMapping(value = "/zxingMake", method = RequestMethod.GET)
    public void  zxingMake(@RequestParam String content) {
        ZxingEAN13EncoderHandler zxingHandle=new ZxingEAN13EncoderHandler();
        zxingHandle.encode("5901234521215",200,100,"d:/test/zxing_EAN13.png");
        //System.out.print(zxingHandle.getSingleNum(1124));

    }

    /**
     * 销售一单成功后，向数据库添加一条销售记录
     */
    @RequestMapping(value = "/salesSuccess", method = RequestMethod.GET)
    public void salesSuccess(){

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
