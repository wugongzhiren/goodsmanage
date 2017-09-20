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
    SalesDetailRepository salesDetalisRepository;
    @Autowired
    GoodsRepository goodsRepository;
    @Autowired
    CustomerVIPRepository customerVIPRepository;

    /**
     * 销售一单成功后，向数据库添加一条销售记录
     *
     * @param cashier          收银员
     * @param vipID            会员自增编号
     * @param payMoney         实际收费
     * @param payWay           收费方式 1；现金，2：支付宝；3：微信
     * @param saleGoodsDetails 销售商品详情JSON
     */
    @Transactional
    @RequestMapping(value = "/salesSuccess", method = RequestMethod.POST)
    public String salesSuccess(@RequestParam String cashier, @RequestParam String vipID, @RequestParam String shouldPay,
                               @RequestParam String payMoney, @RequestParam String payWay, @RequestParam String saleGoodsDetails, @RequestParam String vipSalePrice, @RequestParam String orginPrice) {
        //在收入表增加一条收入记录
        List<Income> income = incomeRepository.findByCurrentDateStr(LocalDate.now().toString());
        String payWayStr = "";
        if (income != null && income.size() > 0) {
            if ("1".equals(payWay)) {
                //现金付款
                payWayStr = "现金";
                income.get(0).setCashMoney(income.get(0).getCashMoney() == null ? new BigDecimal(shouldPay) : income.get(0).getCashMoney().add(new BigDecimal(shouldPay)));
            }
            if ("2".equals(payWay)) {
                //支付宝付款
                payWayStr = "支付宝";
                income.get(0).setPayBabyMoney(income.get(0).getPayBabyMoney() == null ? new BigDecimal(shouldPay) : income.get(0).getPayBabyMoney().add(new BigDecimal(shouldPay)));
            }
            if ("3".equals(payWay)) {
                //微信付款
                payWayStr = "微信";
                income.get(0).setWeChatMoney(income.get(0).getWeChatMoney() == null ? new BigDecimal(shouldPay) : income.get(0).getWeChatMoney().add(new BigDecimal(shouldPay)));
            }
            incomeRepository.save(income.get(0));
        } else {
            Income incomebean = new Income();
            if ("1".equals(payWay)) {
                //现金付款
                payWayStr = "现金";
                incomebean.setCashMoney(new BigDecimal(shouldPay));
            }
            if ("2".equals(payWay)) {
                //支付宝付款
                payWayStr = "支付宝";
                incomebean.setPayBabyMoney(new BigDecimal(shouldPay));
            }
            if ("3".equals(payWay)) {
                //微信付款
                payWayStr = "微信";
                incomebean.setWeChatMoney(new BigDecimal(shouldPay));

            }
            incomebean.setCurrentDateStr(LocalDate.now().toString());
            incomeRepository.save(incomebean);
        }

        List<Goods> retList = Factory.getGson().fromJson(saleGoodsDetails,
                new TypeToken<List<Goods>>() {
                }.getType());

//单号
        long saleid = System.currentTimeMillis();
        double sumDenouncePrice = 0;//优惠价格

        float sumVipDenouncePrice = 0f;//优惠价格
        float sumPrice = 0f;//商品合计价格
        try {


            for (int i = 0; i < retList.size(); i++) {
                //计算特价优惠总钱数
                boolean flag = false;

                //价格合计
                sumPrice = sumPrice + Float.parseFloat(retList.get(i).getSumPrice());

                //计算特价优惠总钱数
                if (retList.get(i).getDenouncePrice() != null && !"".equals(retList.get(i).getDenouncePrice())) {

                    sumDenouncePrice = sumDenouncePrice + Float.parseFloat(retList.get(i).getDenouncePrice());
                    flag = true;
                }
                //有会员价时计算会员特价优惠总钱数
                if (!flag && (retList.get(i).getVipDenouncePrice() != null && !"".equals(retList.get(i).getVipDenouncePrice()))) {

                    sumVipDenouncePrice = sumVipDenouncePrice + Float.parseFloat(retList.get(i).getVipDenouncePrice());
                }
                //没有会员价计算打折折扣总额
                if (!flag && ((retList.get(i).getVipDenouncePrice() == null || "".equals(retList.get(i).getVipDenouncePrice())) && (!"无".equals(vipSalePrice)) && !"0".equals(vipSalePrice))) {
                    sumVipDenouncePrice = sumVipDenouncePrice + (10 - Integer.parseInt(vipSalePrice)) / 10f * Float.parseFloat(retList.get(i).getPrice().toString())*retList.get(i).getGoodsCount();
                }
                //sumDenouncePrice
                //向销售记录详细表增加记录
                SalesGoodDetails details = new SalesGoodDetails();
                details.setSaleid(saleid);
                details.setGoodsID(retList.get(i).getZxingCode());
                details.setGoodsName(retList.get(i).getGoodsName());
                details.setGoodsType(retList.get(i).getGoodsType());
                details.setGoodSprice(retList.get(i).getPrice());
                details.setGoodsCount(retList.get(i).getGoodsCount());//其实只是借用了GOOD类的GoodsCount作为购买数量
                salesDetalisRepository.save(details);

                //更新对应商品库存数量
                Goods goods = goodsRepository.findByZxingCode(retList.get(i).getZxingCode());
                if (goods != null) {
                    goods.setGoodsCount(goods.getGoodsCount() - retList.get(i).getGoodsCount());
                    goodsRepository.save(goods);
                }
                //goodsRepository.updateGoodsCount(retList.get(i).getGoodsCount(),retList.get(i).getZxingCode());
            }
           /* //非会员时优惠价格
            sumDenouncePrice=Math.round((sumPrice-Float.parseFloat(shouldPay)) * 100) * 0.01d;*/
            //1.向销售记录表添加一条数据
            SalesRecord salesRecord = new SalesRecord();

            String creatdate = new Factory().getDate();
            salesRecord.setSalesId(saleid);

            //日期
            salesRecord.setCreatDate(creatdate);
            salesRecord.setCustomerID(vipID == null ? "普通顾客" : vipID);
            //收银员
            //salesRecord.setCashier(cashier);
            //会员折扣额
            //salesRecord.setFavorablePrice(new BigDecimal(favorablePrice));
            //商品合计金额
            salesRecord.setPrice(new BigDecimal(sumPrice));
            salesRecord.setFavorablePrice(new BigDecimal(sumDenouncePrice));
            if (!"普通顾客".equals(vipID)) {
                salesRecord.setVipFavorablePrice(new BigDecimal(sumVipDenouncePrice));
            }
            //实付款
            BigDecimal payM = new BigDecimal(payMoney);
            salesRecord.setPayMoney(payM);
            //付款方式
            salesRecord.setPayWay(payWayStr);
            salesRecord.setSaleCount(retList.size());
            //存入销售明细的JSON.方便以后分析销售记录
            salesRecord.setSalsGoodDetails(saleGoodsDetails);
            saleManageRepository.save(salesRecord);
        } catch (Exception e) {
            e.printStackTrace();
            return Constant.RESULT_FAIL;
        }

        //会员积分增加
        if (!"普通顾客".equals(vipID)) {
            CustomerVIP vip = customerVIPRepository.findOne(Long.parseLong(vipID));
            if (vip != null) {
                vip.setScore(vip.getScore().add(new BigDecimal(shouldPay)));
            }
        }
        //2.打印小票
        //计算找零
        BigDecimal returnMoney = new BigDecimal(payMoney).subtract(new BigDecimal(shouldPay));
/*        //计算会员折扣额
        BigDecimal vipSalePrice1 = new BigDecimal(0.00);

        if ((!"无".equals(vipSalePrice)) && !"0".equals(vipSalePrice)) {
            //有会员折扣
            vipSalePrice1 = new BigDecimal(sumPrice).subtract(new BigDecimal(shouldPay));
        }*/

        printTick(retList, vipID, saleid + "", sumPrice + "", new BigDecimal(sumDenouncePrice).toString(), payMoney, shouldPay,returnMoney.toString(), sumVipDenouncePrice + "", payWay);
        //3.返回处理结果
        return Constant.RESULT_SUCCESS;
    }

    /**
     * 销售一单成功后，打印小票
     * 商品列表  会员ID  订单号  合计价格  折扣额   应付款   会员折扣  找零
     */
    private void printTick(List<Goods> goods, String operatorName, String orderId,
                           String totalPrice, String favorablePrice,
                           String actualCollection, String shouldPay,String giveChange, String vipSalePrice, String payWay) {
        TicksInfo info = new TicksInfo(goods, operatorName, orderId, totalPrice, favorablePrice, actualCollection,shouldPay, giveChange, vipSalePrice, payWay);
        new MyTickesprinter().myprint(info);
    }

    /**
     * 获取当天收入
     */
    @RequestMapping(value = "/getDayIncome", method = RequestMethod.GET)
    public Income getDayIncome() {
        LocalDate todaydate = LocalDate.now();
        Income result = new Income();
        BigDecimal cash = new BigDecimal(0);
        BigDecimal payBaby = new BigDecimal(0);
        BigDecimal wechat = new BigDecimal(0);
        List<Income> income = incomeRepository.findByCurrentDateStr(todaydate.toString());
        if (income != null && income.size() > 0) {
            for (int i = 0; i < income.size(); i++) {
                if (income.get(i).getCashMoney() != null) {
                    cash = cash.add(income.get(i).getCashMoney());
                }
                if (income.get(i).getPayBabyMoney() != null) {
                    payBaby = payBaby.add(income.get(i).getPayBabyMoney());
                }
                if (income.get(i).getWeChatMoney() != null) {
                    wechat = wechat.add(income.get(i).getWeChatMoney());
                }
            }
            result.setCashMoney(cash);
            result.setPayBabyMoney(payBaby);
            result.setWeChatMoney(wechat);
            result.setId(income.get(0).getId());
            result.setCurrentDateStr(todaydate.toString());
            return result;
        } else {
            return null;
        }
    }
}
