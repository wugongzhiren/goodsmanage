package com.manage.controller;

import com.manage.bean.Goods;
import com.manage.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Goods findGoodsInfo(@RequestParam long zxingCode){
return goodsRepository.findByZxingCode(zxingCode);
    }
    /**
     * 查询所有在库商品的信息
     */
    public List<Goods> findAllGoodsinfo(){
return goodsRepository.findAll();
    }
}
