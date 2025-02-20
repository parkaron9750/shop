package com.web.backend.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.web.backend.item.Item;
import com.web.backend.item.ItemSellStatus;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static com.web.backend.item.QItem.item;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(RepositoryTest.class);

    @Autowired
    private EntityManager em;

    @Autowired
    private JPAQueryFactory queryFactory = new JPAQueryFactory(em);

    @Autowired
    WebRepository repository;

    public void createItem(){
        for (int i = 0; i < 10; i++) {
            Item item = new Item();
            item.setItemName("TestItem" + i);
            item.setPrice(10000 + i);
            item.setStock(10 + i);
            item.setStatus(ItemSellStatus.SELL);
            item.setItemDetail("테스트 상세 내용" + i);
            item.setItemRegDate(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());

            repository.save(item);
        }
    }
    @Test
    @DisplayName("JPQL 쿼리 ASC 테스트")
    void findByItemDetailTest(){
        createItem();
        List<Item> itemList = repository.findByItemDetail("테스트");

        for (Item item : itemList) {
            log.info("item={}",item);

        }
        assertThat(itemList.get(0).getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("JPQL 쿼리 NATIVE DESC 테스트")
    void findByItemDetailNativeTest(){
        createItem();
        List<Item> itemList = repository.findByItemDetailNative("테스트");
        for (Item item : itemList) {
            log.info("item={}",item);
        }
        assertThat(itemList.get(0).getItemName()).isEqualTo("TestItem9");
    }

    @Test
    @DisplayName("QueryDSL 테스트")
    void queryDSLTest(){
        createItem();
        List<Item> result = queryFactory
                .selectFrom(item)
                .where(item.status.eq(ItemSellStatus.SELL))
                .orderBy(item.price.desc())
                .fetch();

        for (Item item : result) {
            log.info("item={}",item);
        }

    }

}
