package com.manage.repository;


import com.manage.bean.SalesGoodDetails;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Created by Administrator on 2017/4/23.
 */
public interface SalesDetailRepository extends JpaRepository<SalesGoodDetails,Long> {

}
