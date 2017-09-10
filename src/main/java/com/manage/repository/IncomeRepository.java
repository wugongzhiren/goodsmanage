package com.manage.repository;

import com.manage.bean.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Administrator on 2017/4/23.
 */
public interface IncomeRepository extends JpaRepository<Income,Long> {
    List<Income> findByCurrentDateStr(@Param("currentDate") String currentDate);
}
