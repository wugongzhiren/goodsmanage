package com.manage.repository;

import com.manage.bean.SalesRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by Administrator on 2017/4/23.
 */
public interface SaleManageRepository extends JpaRepository<SalesRecord,Long> {
  /*  User findByUserID(@Param("userID") String userID);
    User findByDormitoryID(@Param("dormitoryID") String dormitoryID);
    User findByUserIDAndPassword(@Param("userID") String userID, @Param("password") String password);*/
}
