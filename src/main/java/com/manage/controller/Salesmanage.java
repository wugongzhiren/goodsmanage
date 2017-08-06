package com.manage.controller;

import com.google.gson.reflect.TypeToken;
import com.manage.bean.Income;
import com.manage.bean.SalesGoodDetails;
import com.manage.bean.SalesRecord;
import com.manage.constant.Constant;
import com.manage.factory.Factory;
import com.manage.repository.IncomeRepository;
import com.manage.repository.SaleManageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 销售管理
 * Created by Administrator on 2017/8/3.
 */
@RestController
public class Salesmanage {
    @Autowired
    private IncomeRepository incomeRepository;
    @Autowired
    private SaleManageRepository saleManageRepository;

    /**
     * 销售一单成功后，向数据库添加一条销售记录
     * @param vipID 会员编号
     * @param originMoney 原价
     * @param favorablePrice 优惠价格
     * @param payMoney 实际收费
     * @param payWay 收费方式 1；现金，2：支付宝；3：微信
     * @param saleGoodsDetails 销售商品详情JSON
     */
    @RequestMapping(value = "/salesSuccess", method = RequestMethod.GET)
    public String salesSuccess(@RequestParam String vipID,@RequestParam String originMoney,@RequestParam String favorablePrice,
    @RequestParam String payMoney,@RequestParam String payWay,@RequestParam String saleGoodsDetails){

        List<SalesGoodDetails> retList = Factory.getGson().fromJson(saleGoodsDetails,
                new TypeToken<List<SalesGoodDetails>>() {
                }.getType());
        //1.向销售记录表添加一条数据
        SalesRecord salesRecord=new SalesRecord();
        salesRecord.setSalesId(System.currentTimeMillis());
        salesRecord.setCreatDate(LocalDate.now().toString());

        salesRecord.setCustomerID(vipID==null?"U"+System.currentTimeMillis():vipID);
        salesRecord.setFavorablePrice(new BigDecimal(favorablePrice));
        salesRecord.setOriginPrice(new BigDecimal(originMoney));
        salesRecord.setPayMoney(new BigDecimal(payMoney));
        salesRecord.setPayWay(payWay);
        salesRecord.setSaleCount(retList.size());
        //存入销售明细的JSON.方便以后分析销售记录
        salesRecord.setSalsGoodDetails(saleGoodsDetails);
        try {
            saleManageRepository.save(salesRecord);
        }
        catch (Exception e)
        {
            return Constant.RESULT_FAIL;
        }
        //2.打印小票
        printTick();
        //3.返回处理结果
        return Constant.RESULT_SUCCESS;
    }

    /**
     * 销售一单成功后，打印小票
     */
    private void printTick(){

    }

    /**
     * 获取当天收入
     */
    @RequestMapping(value = "/getDayIncome", method = RequestMethod.GET)
    public Income getDayIncome(){
System.out.print("测试");
        LocalDate todaydate=LocalDate.now();
        System.out.print(todaydate.toString());
        Income income=incomeRepository.findByCurrentDateStr(todaydate.toString());
if(income==null) {
    return null;
}
else {
    return income;
}
    }
}
