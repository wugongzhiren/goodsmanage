package com.manage.controller;

import com.google.gson.reflect.TypeToken;
import com.manage.bean.*;
import com.manage.constant.Constant;
import com.manage.factory.Factory;
import com.manage.print.MyTickesprinter;
import com.manage.print.TicksInfo;
import com.manage.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
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
    @Autowired
    GoodsRepository goodsRepository;
    @Autowired
    CustomerVIPRepository customerVIPRepository;
    /**
     * 销售一单成功后，向数据库添加一条销售记录
     *  @param cashier 收银员
     * @param vipID 会员自增编号
     * @param payMoney 实际收费
     * @param payWay 收费方式 1；现金，2：支付宝；3：微信
     * @param saleGoodsDetails 销售商品详情JSON
     */
    @Transactional
    @RequestMapping(value = "/salesSuccess", method = RequestMethod.POST)
    public String salesSuccess(@RequestParam String cashier,@RequestParam String vipID,@RequestParam String shouldPay,
    @RequestParam String payMoney,@RequestParam String payWay,@RequestParam String saleGoodsDetails,@RequestParam String vipSalePrice,@RequestParam String orginPrice){
        System.out.println("结账开始");
        System.out.println(saleGoodsDetails);
        System.out.println("");
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

        //合计价格，不是应付款,应该是会员打折前的合计价格
        BigDecimal originPrice=new BigDecimal(0.00);
        System.out.print(saleGoodsDetails);
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
        float sumDenouncePrice=0f;//优惠价格
        try {
            saleManageRepository.save(salesRecord);

            for(int i=0;i<retList.size();i++){
if(retList.get(i).getDenouncePrice()!=null&&!"".equals(retList.get(i).getDenouncePrice())) {

    sumDenouncePrice = sumDenouncePrice + Float.parseFloat(retList.get(i).getDenouncePrice());
}
                //sumDenouncePrice
                originPrice.add(retList.get(i).getPrice());
                //向销售记录详细表增加记录
                SalesGoodDetails details=new SalesGoodDetails();
                details.setSaleid(saleid);
                System.out.print("条码"+retList.get(i).getZxingCode());
                details.setGoodsID(retList.get(i).getZxingCode());
                details.setGoodsName(retList.get(i).getGoodsName());
                details.setGoodsType(retList.get(i).getGoodsType());
                details.setGoodSprice(retList.get(i).getPrice());
                details.setGoodsCount(retList.get(i).getGoodsCount());//其实只是借用了GOOD类的GoodsCount作为购买数量
                salesDetalisRepository.save(details);

                //更新对应商品库存数量
                Goods goods=goodsRepository.findByZxingCode(retList.get(i).getZxingCode());
                if(goods!=null){
                    goods.setGoodsCount(goods.getGoodsCount()-retList.get(i).getGoodsCount());
                    goodsRepository.save(goods);
                }
                //goodsRepository.updateGoodsCount(retList.get(i).getGoodsCount(),retList.get(i).getZxingCode());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return Constant.RESULT_FAIL;
        }

        //会员积分增加
        if(!"普通顾客".equals(vipID)) {
            CustomerVIP vip=customerVIPRepository.findOne(Long.parseLong(vipID));
            if(vip!=null){
                vip.setScore(vip.getScore().add(new BigDecimal(shouldPay)));
            }
        }
        //2.打印小票
        //计算找零
        BigDecimal returnMoney=payM.subtract(new BigDecimal(shouldPay));
        //计算会员折扣额
        BigDecimal vipSalePrice1=new BigDecimal(0.00);

        if((!"无".equals(vipSalePrice))&&!"0".equals(vipSalePrice)){
            //有会员折扣
            vipSalePrice1=new BigDecimal(orginPrice).subtract(new BigDecimal(shouldPay));
        }

        printTick(retList,cashier,saleid+"",shouldPay,new BigDecimal(sumDenouncePrice).toString(),payMoney,returnMoney.toString(),vipSalePrice1.toString(),payWay);
        //3.返回处理结果
        return Constant.RESULT_SUCCESS;
    }

    /**
     * 销售一单成功后，打印小票
     */
    private void printTick(List<Goods> goods, String operatorName, String orderId,
                           String totalPrice, String favorablePrice,
                           String actualCollection, String giveChange,String vipSalePrice,String payWay){
        System.out.print("开始打印");
        TicksInfo info=new TicksInfo(goods,operatorName,orderId,totalPrice,favorablePrice,actualCollection,giveChange,vipSalePrice,payWay);
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
