package com.web.backend.repository;

import com.web.backend.item.entity.Item;
import com.web.backend.item.itemconst.ItemSellStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Transactional
public class RepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(RepositoryTest.class);
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
    @DisplayName("JPQL 쿼리 테스트")
    void findByItemDetailTest(){
        createItem();
        List<Item> itemList = repository.findByItemDetail("테스트");

        for (Item item : itemList) {
            log.info("item={}",item);

        }
    }

}
