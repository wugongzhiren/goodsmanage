package com.manage.controller;

import com.manage.bean.CustomerVIP;
import com.manage.constant.Constant;
import com.manage.repository.CustomerVIPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * 会员管理
 * Created by Administrator on 2017/8/6.
 */
@RestController
public class CustomerVIPManage {
    @Autowired
    CustomerVIPRepository repository;

    /**
     * 创建会员账号
     * @param identityCode 会员识别码
     * @return
     */
    @RequestMapping(value = "/creatVIP", method = RequestMethod.GET)
    public String creatVIP(@RequestParam String identityCode){
        CustomerVIP vip=new CustomerVIP();
        vip.setVipID(identityCode);
        vip.setScore(0);
        vip.setCreatDate(LocalDate.now().toString());
        try {
            repository.save(vip);
            return Constant.RESULT_SUCCESS;
        }
        catch (Exception e){
            return Constant.RESULT_FAIL;
        }
    }

    /**
     * 查询会员积分
     * @param identityCode 会员识别码
     * @return
     */
    @RequestMapping(value = "/getVIPScore", method = RequestMethod.GET)
    public String getVIPScore(@RequestParam String identityCode) {
        return repository.findByVipID(identityCode).getScore() + "";
    }

    /**
     * 增加会员积分
     * @param score 积分新增值
     * @return
     */
    public String addScore(String identityCode,String score){
        try {
        long scoreTemp=repository.findByVipID(identityCode).getScore();
        long scoreRe=scoreTemp+Long.parseLong(score);
        CustomerVIP vip=new CustomerVIP();
        vip.setVipID(identityCode);
        vip.setScore(scoreRe);
            repository.save(vip);
            return Constant.RESULT_SUCCESS;
        }
        catch (Exception e){
            return Constant.RESULT_FAIL;
        }
    }

    /**
     * 消耗会员积分（待定）
     * @param score 积分消耗值
     * @return
     */
    public String consumeScore(String identityCode,String score){
return null;
    }
}
