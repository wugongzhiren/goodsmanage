package com.manage.repository;

import com.manage.bean.CustomerVIP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by Administrator on 2017/4/23.
 */
public interface CustomerVIPRepository extends JpaRepository<CustomerVIP,Long> {
    CustomerVIP findByVipID(@Param("vipID") String vipID);
    CustomerVIP findByPhoneNum(@Param("phoneNum") String phoneNum);
   /* CustomerVIP findByUserID(@Param("userID") String userID);
    CustomerVIP findByDormitoryID(@Param("dormitoryID") String dormitoryID);
    CustomerVIP findByUserIDAndPassword(@Param("userID") String userID, @Param("password") String password);*/
}
