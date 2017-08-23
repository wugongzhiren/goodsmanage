package com.manage.controller;

import com.manage.bean.Setting;
import com.manage.constant.Constant;
import com.manage.repository.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/8/22.
 */
@RestController
public class SettingManage {
    @Autowired
    SettingRepository settingRepository;
    /**
     * 获取当天收入
     * @param ratio 折扣率
     */
    @RequestMapping(value = "/setSaleRatio", method = RequestMethod.GET)
    public String setSaleRatio(@RequestParam String ratio) {
        Setting setting=settingRepository.findBySetType(Constant.SETTING_SALE);
        if(setting!=null){
            setting.setSetContent(ratio);
        }
        else {
            setting=new Setting();
            setting.setSetContent(ratio);
            setting.setSetType(Constant.SETTING_SALE);
        }
        settingRepository.save(setting);
        return Constant.RESULT_SUCCESS;
    }
}
