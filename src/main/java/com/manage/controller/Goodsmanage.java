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

import java.util.List;

/**
 * Created by Administrator on 2017/8/4.
 */
@RestController
public class Goodsmanage {
    @Autowired
    private GoodsRepository goodsRepository;
    /**
     * 根据商品条码查询对应商品的信息，主要是查库存
     * @return
     */
    @RequestMapping(value = "/getGoodsInfo", method = RequestMethod.GET)
    public Goods getGoodsInfo(@RequestParam String zxingCode){
Goods goods=goodsRepository.findByZxingCode(zxingCode);
if(goods!=null) {
    goods.setResultCode(Constant.RESULT_SUCCESS);
    return goodsRepository.findByZxingCode(zxingCode);
}
else {
    Goods goods1=new Goods();
    goods1.setResultCode(Constant.RESULT_FAIL);
    return goods1;
}
    }
    /**
     * 查询所有在库商品的信息
     */
    public List<Goods> findAllGoodsinfo(){
return goodsRepository.findAll();
    }

    /**
     *生成商品Zxing条码
     * @return
     */
    @RequestMapping(value = "/zxingMake", method = RequestMethod.GET)
    public void  zxingMake(@RequestParam String content) {
        ZxingEAN13EncoderHandler zxingHandle=new ZxingEAN13EncoderHandler();
        zxingHandle.encode("5901234521215",200,100,"d:/test/zxing_EAN13.png");
        //System.out.print(zxingHandle.getSingleNum(1124));

    }
}
