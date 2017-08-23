package com.manage.repository;

import com.manage.bean.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Administrator on 2017/4/23.
 */
public interface GoodsRepository extends JpaRepository<Goods,Long> {
    Goods findByZxingCode(@Param("zxingCode") String zxingCode);

    @Query(value = "select * from goods u where u.goods_name like %?1%",nativeQuery = true)
    List<Goods> findByGoodsNameLike(@Param("goodsName") String goodsName);

    /*@Query(value = "update goods u set u.goods_count=u.goods_count-?1 where u.zxing_code=?2 ",nativeQuery = true)
    void updateGoodsCount(@Param("count") int count,@Param("zxing_code") String zxing_code);*/
}
