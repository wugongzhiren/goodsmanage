package com.manage.controller;

import com.manage.bean.CustomerVIP;
import com.manage.bean.Setting;
import com.manage.constant.Constant;
import com.manage.repository.CustomerVIPRepository;
import com.manage.repository.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 会员管理
 * Created by Administrator on 2017/8/6.
 */
@RestController
public class CustomerVIPManage {
    @Autowired
    CustomerVIPRepository repository;
    @Autowired
    SettingRepository settingRepository;
    /**
     * 创建会员账号
     *
     * @param identityCode 会员识别码
     * @param  vipID 手机号码
     * @return
     */
    @RequestMapping(value = "/creatVIP", method = RequestMethod.GET)
    public String creatVIP(@RequestParam String identityCode,@RequestParam String vipID) {
        CustomerVIP vip = null;
        if(identityCode!=null&&!"".equals(identityCode)) {
            vip = repository.findByVipID(identityCode);
            if (vip != null) {
                return Constant.RESULT_EXIST;
            }
        }
        if(vipID!=null&&!"".equals(vipID)){
            vip=repository.findByPhoneNum(vipID);
            if (vip != null) {
                return Constant.RESULT_EXIST;
            }
        }
        vip = new CustomerVIP();
        vip.setVipID("".equals(identityCode)?null:identityCode);
        vip.setScore(new BigDecimal(0));
        vip.setPhoneNum("".equals(vipID)?null:vipID);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        vip.setCreatDate(sdf.format(new Date()));
        try {
            repository.save(vip);
            return Constant.RESULT_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Constant.RESULT_FAIL;
        }
    }

    /**
     * 会员登录
     *
     * @param identityCode
     * @return
     */
    @RequestMapping(value = "/vipLogin", method = RequestMethod.GET)
    public CustomerVIP vipLogin(@RequestParam String identityCode) {

        CustomerVIP vip = repository.findByVipID(identityCode);
        if (vip == null) {
            vip=repository.findByPhoneNum(identityCode);
            if(vip == null) {
                vip = new CustomerVIP();
                vip.setResultCode(Constant.RESULT_FAIL);
                return vip;
            }
            return vip;
        } else {
            Setting setting=settingRepository.findBySetType(Constant.SETTING_SALE);
            if(setting!=null){
                vip.setSaleRatio(new BigDecimal(setting.getSetContent()));
            }
            vip.setSaleRatio(new BigDecimal("10"));
            vip.setResultCode(Constant.RESULT_SUCCESS);
            return vip;
        }
    }

    /**
     * 查询会员积分
     *
     * @param identityCode 会员识别码或者手机号码
     * @return
     */
    @RequestMapping(value = "/getVIPInfo", method = RequestMethod.GET)
    public List<CustomerVIP> getVIPScore(@RequestParam String identityCode) {
        List<CustomerVIP> vipList = new ArrayList<>();
        CustomerVIP vip;
        if(identityCode==""||identityCode==null){
            return repository.findAll();
        }
            vip=repository.findByVipID(identityCode);
            if(vip==null){
                return vipList;
            }
            vipList.add(vip);
            return vipList;
            //boolean

        //return repository.findByVipID(identityCode).getScore() + "";
    }

    /**
     * 增加会员积分
     *
     * @param score 积分新增值
     * @return
     */
    public String addScore(String identityCode, String score) {
        try {
           // long scoreTemp = repository.findByVipID(identityCode).getScore();
            //long scoreRe = scoreTemp + Long.parseLong(score);
            CustomerVIP vip = new CustomerVIP();
            vip.setVipID(identityCode);
            //vip.setScore(scoreRe);
            repository.save(vip);
            return Constant.RESULT_SUCCESS;
        } catch (Exception e) {
            return Constant.RESULT_FAIL;
        }
    }

    /**
     * 消耗会员积分（待定）
     *
     * @param score 积分消耗值
     * @return
     */
    public String consumeScore(String identityCode, String score) {
        return null;
    }
}
