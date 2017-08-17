package com.manage.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 系统设置，包括优惠设置
 * Created by Administrator on 2017/8/14.
 */
@Entity
public class Setting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String setType;//设置类型
    private String setContent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSetType() {
        return setType;
    }

    public void setSetType(String setType) {
        this.setType = setType;
    }

    public String getSetContent() {
        return setContent;
    }

    public void setSetContent(String setContent) {
        this.setContent = setContent;
    }
}
