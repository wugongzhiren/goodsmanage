package com.manage.repository;

import com.manage.bean.Goods;
import com.manage.bean.Numindex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by Administrator on 2017/4/23.
 */
public interface NumberIndexRepository extends JpaRepository<Numindex,Long> {

}
