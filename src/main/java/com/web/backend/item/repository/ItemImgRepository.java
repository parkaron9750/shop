package com.web.backend.item.repository;

import com.web.backend.item.entity.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemImgRepository extends JpaRepository<ItemImg, Long>{

    /**
     * FK 로 가져오기
     * 오름차순으로 가져오기
     */
    @Query(value = "select * from item_img where item_id = :itemId order by item_id asc",nativeQuery = true)
    List<ItemImg> findByItemIdOrderByIdAsc(@Param("itemId") Long itemId);
}
