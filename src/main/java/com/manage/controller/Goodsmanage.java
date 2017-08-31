package com.manage.controller;

import com.google.zxing.FormatException;
import com.manage.bean.Goods;
import com.manage.constant.Constant;
import com.manage.print.GoodsInfoPrint;
import com.manage.print.MyTickesprinter;
import com.manage.print.Pic;
import com.manage.print.Print;
import com.manage.repository.GoodsRepository;
import com.manage.zxing.ZxingEAN13EncoderHandler;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
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
            //如果有促销价格，则设置为促销价格
            if (goods.getSalePrice() != null) {
                goods.setPrice(new BigDecimal(goods.getSalePrice()));
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

        //System.out.print(currPage);
        if ("".equals(goodsName) || goodsName == null) {
            return goodsRepository.findAll();
        } else {

            return goodsRepository.findByGoodsNameLike(goodsName);
        }
    }

    /**
     * 查询所有在库商品的信息
     *
     * @param goodsName 条形码
     */
    @RequestMapping(value = "/getGoodsInfoAll", method = RequestMethod.GET)
    public List<Goods> findAllGoodsinfo(@RequestParam String goodsName) {
        if ("".equals(goodsName) || goodsName == null) {
            return goodsRepository.findAll();
        } else {
            List<Goods> goods = new ArrayList<>();
            Goods goods1 = goodsRepository.findByZxingCode(goodsName);
            if (goods1 == null) {
                return goodsRepository.findByGoodsNameLike(goodsName);
            }
            else {
                goods.add(goods1);
                return goods;
            }
        }
    }

    /**
     * 生成商品Zxing条码
     *
     * @return
     */
    @RequestMapping(value = "/zxingMake", method = RequestMethod.POST)
    public String zxingMake(@RequestParam String content, @RequestParam String goodsVersion, @RequestParam String price, @RequestParam String count) throws FormatException {
        try {
            ZxingEAN13EncoderHandler zxingHandle = new ZxingEAN13EncoderHandler();
            //zxingHandle.encode(content, 116, 24, "d:/zxing/zxing_EAN13.png");
            zxingHandle.encode2(content, 24, 24, "d:/zxing2/");
            //zxingHandle.encode(content, 60, 24, "d:/zxing/zxing_EAN13.png");
            //System.out.print(zxingHandle.getSingleNum(1124));
           // MyTickesprinter print=new MyTickesprinter();
           // print.mygoodsprint(new GoodsInfoPrint(price.toString(),goodsVersion,content));
            Pic.makeZxingPic2(content, goodsVersion, price.toString());
            MyTickesprinter myTickesprinter=new MyTickesprinter();
            myTickesprinter.mygoodsprint(new GoodsInfoPrint(content,goodsVersion,price));
           // Pic.makeZxingPic(content, goodsVersion, price.toString());
           // Print.printCommon("d:/goodsinfo.png", null, Integer.parseInt(count));
            return Constant.RESULT_SUCCESS;
        } catch (Exception e) {
            return Constant.RESULT_FAIL;
        }
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
    public String instore(@RequestParam String zxing,@RequestParam String goodsName, @RequestParam String goodsType, @RequestParam String goodsVersion,
                          @RequestParam String goodsCount, @RequestParam String price) throws FormatException {
        //已经有条码了
        if(zxing!=""&&zxing!=null){

            Goods goods = new Goods();
            if(goodsRepository.findByZxingCode(zxing)!=null){
                return Constant.RESULT_EXIST;
            }
            goods.setZxingCode(zxing);
            //名称
            goods.setGoodsName(goodsName);
            //分类
            goods.setGoodsType(goodsType);
            //型号
            goods.setGoodsVersion(goodsVersion);
            //可能出现数字转化异常
            goods.setGoodsCount(Integer.parseInt(goodsCount));
            goods.setPrice(new BigDecimal(price));
            goods.setInstoreDate(LocalDate.now().toString());
            //保存商品信息到数据库
            goodsRepository.save(goods);
            return Constant.RESULT_SUCCESS;
        }else {
            //构造入库条形码
            ZxingEAN13EncoderHandler handler = new ZxingEAN13EncoderHandler();
            String zxingCode = handler.generateZxing();
            String path = "d:/zxing/";
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            String path1 = "d:/goodspic/";
            File file1 = new File(path1);
            if (!file1.exists()) {
                file1.mkdirs();
            }
            if (Constant.RESULT_FAIL.equals(zxingCode)) {
                return Constant.RESULT_FAIL;
            }
            //生成条形码图片
            handler.encode(zxingCode, 116, 24, "d:/zxing/zxing_EAN13.png");
            //handler.encode(zxingCode, 60, 24, "d:/zxing/zxing_EAN13.png");
            //生成标签图片
            Pic.makeZxingPic(zxingCode, goodsVersion, price.toString());

            //打印条码
            //MyTickesprinter myTickesprinter=new MyTickesprinter();
            //myTickesprinter.mygoodsprint(new GoodsInfoPrint(zxingCode));
            try {
                //条码中加文字
               // Print.makeZxingPic(zxingCode, goodsVersion, price.toString());
               // Print.printCommon("d:/goodsinfo.png", null, Integer.parseInt(goodsCount));

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
                goods.setInstoreDate(LocalDate.now().toString());
                //保存商品信息到数据库
                goodsRepository.save(goods);

                //生成条码并打印
                //encode
                return Constant.RESULT_SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
                return Constant.RESULT_FAIL;
            }
        }
    }

    /**
     * 商品非第一次入库，增加数量,并且打印增加数量份数的标签
     *
     * @return
     */
    @RequestMapping(value = "/addCount", method = RequestMethod.GET)
    public String addCount(@RequestParam String zxingCode, @RequestParam String addCount, @RequestParam String price, @RequestParam String goodVersion) {
        Goods goods = goodsRepository.findByZxingCode(zxingCode);
        if (goods != null) {
            //更新新入库数量
            goods.setGoodsCount(goods.getGoodsCount() + Integer.parseInt(addCount));
            goodsRepository.save(goods);

            //打印
            /*try {
                zxingMake(zxingCode, goodVersion, price, addCount);
            } catch (FormatException e) {
                e.printStackTrace();
                return Constant.PRINT_FAIL;
            }*/
            return Constant.RESULT_SUCCESS;
        } else {
            return Constant.RESULT_FAIL;
        }

    }

    /**
     * 商品非第一次入库，更新商品原价格
     *
     * @return
     */
    @RequestMapping(value = "/updatePrice", method = RequestMethod.GET)
    public String updatePrice(@RequestParam String zxingCode, @RequestParam String newprice) {
        Goods goods = goodsRepository.findByZxingCode(zxingCode);
        if (goods != null) {
            //更新价格
            goods.setPrice(new BigDecimal(newprice));
            goodsRepository.save(goods);


            return Constant.RESULT_SUCCESS;
        } else {
            return Constant.RESULT_FAIL;
        }
    }

    /**
     * 设置优惠价格
     *
     * @return
     */
    @RequestMapping(value = "/setSalePrice", method = RequestMethod.GET)
    public String setSalePrice(@RequestParam String zxingCode, @RequestParam String saleprice) {
        Goods goods = goodsRepository.findByZxingCode(zxingCode);
        if (goods != null) {
            goods.setSalePrice(saleprice);
            goods.setDenouncePrice(goods.getPrice().subtract(new BigDecimal(saleprice)).toString());
            goodsRepository.save(goods);
        }
        return Constant.RESULT_SUCCESS;


    }
    /**
     * 设置优惠价格
     *
     * @return
     */
    @RequestMapping(value = "/resumePrice", method = RequestMethod.GET)
    public String resumePrice(@RequestParam String zxingCode) {
        Goods goods = goodsRepository.findByZxingCode(zxingCode);
        if (goods != null) {
            goods.setSalePrice(null);
            goodsRepository.save(goods);
        }
        return Constant.RESULT_SUCCESS;


    }

}
