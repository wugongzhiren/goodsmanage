package com.manage.repository;

import com.manage.bean.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by Administrator on 2017/4/23.
 */
public interface SettingRepository extends JpaRepository<Setting,Long> {
    Setting findBySetType(@Param("setType") String setType);
}
