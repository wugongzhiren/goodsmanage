package com.manage.controller;

import com.manage.bean.SalesRecord;
import com.manage.bean.Setting;
import com.manage.constant.Constant;
import com.manage.repository.SalesRecordRepository;
import com.manage.repository.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/22.
 */
@RestController
public class RecordManage {
    @Autowired
    SalesRecordRepository repository;
    /**
     * 获取销售记录
     */
    @RequestMapping(value = "/getSaleInfoAll", method = RequestMethod.GET)
    public List<SalesRecord> getSaleInfoAll() {
        List<SalesRecord> salesRecords=repository.findAll();
        if(salesRecords.size()>0){
           return salesRecords;
        }
        salesRecords=new ArrayList<>();
        return salesRecords;
    }
}
