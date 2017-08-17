package com.manage.controller;

import com.google.gson.reflect.TypeToken;
import com.manage.bean.Goods;
import com.manage.bean.Income;
import com.manage.bean.SalesGoodDetails;
import com.manage.bean.SalesRecord;
import com.manage.constant.Constant;
import com.manage.factory.Factory;
import com.manage.print.MyTickesprinter;
import com.manage.print.TicksInfo;
import com.manage.repository.IncomeRepository;
import com.manage.repository.SaleManageRepository;
import com.manage.repository.SalesDetalisRepository;
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
    @Autowired
    SalesDetalisRepository salesDetalisRepository;
    /**
     * 销售一单成功后，向数据库添加一条销售记录
     *  @param cashier 收银员
     * @param vipID 会员编号
     * @param payMoney 实际收费
     * @param payWay 收费方式 1；现金，2：支付宝；3：微信
     * @param saleGoodsDetails 销售商品详情JSON
     */
    @RequestMapping(value = "/salesSuccess", method = RequestMethod.POST)
    public String salesSuccess(@RequestParam String cashier,@RequestParam String vipID,@RequestParam String shouldPay,
    @RequestParam String payMoney,@RequestParam String payWay,@RequestParam String saleGoodsDetails){
        System.out.print("结账开始");
        //在收入表增加一条收入记录
        Income income=incomeRepository.findByCurrentDateStr(LocalDate.now().toString());
        if(income!=null){
            if ("1".equals(payWay)){
                //现金付款
                income.setCashMoney(income.getCashMoney()==null?new BigDecimal(shouldPay):income.getCashMoney().add(new BigDecimal(shouldPay)));
            }
            if ("2".equals(payWay)){
                //支付宝付款
                income.setPayBabyMoney(income.getPayBabyMoney()==null?new BigDecimal(shouldPay):income.getPayBabyMoney().add(new BigDecimal(shouldPay)));
            }
            if ("3".equals(payWay)){
                //微信付款
                income.setWeChatMoney(income.getWeChatMoney()==null?new BigDecimal(shouldPay):income.getWeChatMoney().add(new BigDecimal(shouldPay)));
            }
            incomeRepository.save(income);
        }
        else {
            income=new Income();
            if ("1".equals(payWay)){
                //现金付款
                income.setCashMoney(new BigDecimal(shouldPay));
            }
            if ("2".equals(payWay)){
                //支付宝付款
                income.setPayBabyMoney(new BigDecimal(shouldPay));
            }
            if ("3".equals(payWay)){
                //微信付款
                income.setWeChatMoney(new BigDecimal(shouldPay));

            }
            income.setCurrentDateStr(LocalDate.now().toString());
            incomeRepository.save(income);
        }

        //合计价格，不是应付款
        BigDecimal originPrice=new BigDecimal(0.00);
        List<Goods> retList = Factory.getGson().fromJson(saleGoodsDetails,
                new TypeToken<List<Goods>>() {
                }.getType());

        //1.向销售记录表添加一条数据
        SalesRecord salesRecord=new SalesRecord();
        //单号
        long saleid=System.currentTimeMillis();
        String creatdate=LocalDate.now().toString();
        salesRecord.setSalesId(saleid);
        //日期
        salesRecord.setCreatDate(creatdate);
        salesRecord.setCustomerID(vipID==null?"普通顾客":vipID);

        //收银员
        salesRecord.setCashier(cashier);
        //会员折扣额
        //salesRecord.setFavorablePrice(new BigDecimal(favorablePrice));
        //商品合计金额
        salesRecord.setOriginPrice(originPrice);
        //实付款
        BigDecimal payM=new BigDecimal(payMoney);
        salesRecord.setPayMoney(payM);
        //付款方式
        salesRecord.setPayWay(payWay);
        salesRecord.setSaleCount(retList.size());
        //存入销售明细的JSON.方便以后分析销售记录
        salesRecord.setSalsGoodDetails(saleGoodsDetails);
        try {
            saleManageRepository.save(salesRecord);
            for(int i=0;i<retList.size();i++){
                originPrice.add(retList.get(i).getPrice());
                //向销售记录详细表增加记录
                SalesGoodDetails details=new SalesGoodDetails();
                details.setSaleid(saleid);
                details.setGoodsID(retList.get(i).getZxingCode());
                details.setGoodsName(retList.get(i).getGoodsName());
                details.setGoodsType(retList.get(i).getGoodsType());
                details.setGoodSprice(retList.get(i).getPrice());
                details.setGoodsCount(retList.get(i).getGoodsCount());//其实只是借用了GOOD类的GoodsCount作为购买数量
                salesDetalisRepository.save(details);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return Constant.RESULT_FAIL;
        }
        //2.打印小票
        //计算找零
        BigDecimal returnMoney=payM.subtract(new BigDecimal(shouldPay));
        printTick(retList,cashier,saleid+"",shouldPay,null,payMoney,returnMoney.toString());
        //3.返回处理结果
        System.out.print("处理结束");
        return Constant.RESULT_SUCCESS;
    }

    /**
     * 销售一单成功后，打印小票
     */
    private void printTick(List<Goods> goods, String operatorName, String orderId,
                           String totalPrice, String favorablePrice,
                           String actualCollection, String giveChange){
        System.out.print("开始打印");
        TicksInfo info=new TicksInfo(goods,operatorName,orderId,totalPrice,null,actualCollection,giveChange);
        new MyTickesprinter().myprint(info);
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
