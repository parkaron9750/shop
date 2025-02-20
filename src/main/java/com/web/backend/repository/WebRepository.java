package com.web.backend.repository;

import com.web.backend.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface WebRepository extends JpaRepository<Item, Long> {
    /**
     * LessThan - 가격이 낮을 것을 database에서 가져온다
     * findBy - database 에서 값을 가져온다.
     * findByValueOrCase - database 에서 value 값이나 case 값하고 일치한 것을 가져온다.
     * ValueDesc - Value값을 내림차순으로 정렬해서 가져온다
     */
    @Query("select i from Item i where i.itemDetail like %:itemDetail% "
            + "order by i.price asc")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);
}
