package com.manage.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 递增序号不重复
 * Created by Administrator on 2017/8/11.
 */
@Entity
public class Numindex {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int indexID;//5位整数

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIndexID() {
        return indexID;
    }

    public void setIndexID(int indexID) {
        this.indexID = indexID;
    }
}
