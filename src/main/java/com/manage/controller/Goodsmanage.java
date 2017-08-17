package com.manage.controller;

import com.manage.bean.Goods;
import com.manage.constant.Constant;
import com.manage.repository.GoodsRepository;
import com.manage.zxing.ZxingEAN13EncoderHandler;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2017/8/4.
 */
@RestController
public class Goodsmanage {
    @Autowired
    private GoodsRepository goodsRepository;

    /**
     * 根据商品条码查询对应商品的信息，主要是查库存,收款时扫码调用
     *
     * @return
     */
    @RequestMapping(value = "/getInstoreInfo", method = RequestMethod.GET)
    public Goods getInstoreInfo(@RequestParam String zxingCode) {
        Goods goods = goodsRepository.findByZxingCode(zxingCode);
        if (goods != null) {
            goods.setResultCode(Constant.RESULT_SUCCESS);
            if(goods.getSalePrice()!=null){
                goods.setPrice(goods.getSalePrice());
            }
            return goods;
        } else {
            Goods goods1 = new Goods();
            goods1.setResultCode(Constant.RESULT_FAIL);
            return goods1;
        }
    }

    /**
     * 根据商品名模糊查询对应商品的信息，主要是为了增加数量
     *
     * @return
     */
    @RequestMapping(value = "/getGoodsInfo", method = RequestMethod.GET)
    public List<Goods> getGoodsInfo(@RequestParam String goodsName) {
        System.out.print(goodsName);
            if ("".equals(goodsName) || goodsName == null) {
                return goodsRepository.findAll();
            } else {

                return goodsRepository.findByGoodsNameLike(goodsName);
            }
    }

    /**
     * 查询所有在库商品的信息
     */
    public List<Goods> findAllGoodsinfo() {
        return goodsRepository.findAll();
    }

    /**
     * 生成商品Zxing条码
     *
     * @return
     */
    @RequestMapping(value = "/zxingMake", method = RequestMethod.GET)
    public void zxingMake(@RequestParam String content) {
        ZxingEAN13EncoderHandler zxingHandle = new ZxingEAN13EncoderHandler();
        zxingHandle.encode("5901234521215", 200, 100, "d:/test/zxing_EAN13.png");
        //System.out.print(zxingHandle.getSingleNum(1124));

    }

    /**
     * 商品第一次入库
     *
     * @param goodsName
     * @param goodsType
     * @param goodsVersion
     * @param goodsCount
     * @param price
     */
    @RequestMapping(value = "/instore", method = RequestMethod.POST)
    public String instore(@RequestParam String goodsName, @RequestParam String goodsType, @RequestParam String goodsVersion,
                          @RequestParam String goodsCount, @RequestParam String price) {
        //构造入库条形码
        ZxingEAN13EncoderHandler handler = new ZxingEAN13EncoderHandler();
        String zxingCode = handler.generateZxing();
        if (Constant.RESULT_FAIL.equals(zxingCode)) {
            return Constant.RESULT_FAIL;
        }
        try {
            Goods goods = new Goods();
            goods.setZxingCode(zxingCode);
            //名称
            goods.setGoodsName(goodsName);
            //分类
            goods.setGoodsType(goodsType);
            //型号
            goods.setGoodsVersion(goodsVersion);
            //可能出现数字转化异常
            goods.setGoodsCount(Integer.parseInt(goodsCount));
            goods.setPrice(new BigDecimal(price));
            goodsRepository.save(goods);
            return Constant.RESULT_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Constant.RESULT_FAIL;
        }
    }

    /**
     * 商品非第一次入库，增加数量
     *
     * @return
     */
    @RequestMapping(value = "/addCount", method = RequestMethod.GET)
    public String addCount(@RequestParam String zxingCode,@RequestParam String addCount) {
        Goods goods=goodsRepository.findByZxingCode(zxingCode);
        if(goods!=null){
            //更新新入库数量
            goods.setGoodsCount(goods.getGoodsCount()+Integer.parseInt(addCount));
            return Constant.RESULT_SUCCESS;
        }
        else{
            return Constant.RESULT_FAIL;
        }

    }
}
