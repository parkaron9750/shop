package com.web.backend.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.web.backend.item.constrat.ItemSellStatus;
import com.web.backend.item.entity.Item;
import com.web.backend.item.repository.ItemRepository;
import com.web.backend.member.entity.Member;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

import static com.web.backend.item.entity.QItem.item;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class MemberRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(MemberRepositoryTest.class);

    @Autowired
    private EntityManager em;


    @Autowired
    private JPAQueryFactory queryFactory = new JPAQueryFactory(em);

    @Autowired
    ItemRepository repository;

    public void createItem(){
        for (int i = 0; i < 10; i++) {
            Item item = new Item();
            item.setItemName("TestItem" + i);
            item.setPrice(10000 + i);
            item.setStock(10 + i);
            item.setStatus(ItemSellStatus.SELL);
            item.setItemDetail("테스트 상세 내용" + i);
            item.setUpdateTime(LocalDateTime.now());

            repository.save(item);
        }
    }

    public void createItemList(){
        for (int i = 1; i < 5; i++) {
            Item item = new Item();
            item.setItemName("TestItem" + i);
            item.setPrice(10000 + i);
            item.setStock(10 + i);
            item.setStatus(ItemSellStatus.SELL);
            item.setItemDetail("테스트 상세 내용" + i);
            item.setUpdateTime(LocalDateTime.now());
            repository.save(item);
        }
        for (int i = 6; i < 10; i++) {
            Item item = new Item();
            item.setItemName("TestItem" + i);
            item.setPrice(10000 + i);
            item.setStock(0);
            item.setStatus(ItemSellStatus.SOLD_OUT);
            item.setItemDetail("테스트 상세 내용" + i);
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
    @DisplayName("QueryDSL desc() 테스트")
    void queryDSLTest(){
        createItem();
        List<Item> result = queryFactory
                .selectFrom(item)
                .where(item.status.eq(ItemSellStatus.SELL),
                        item.itemDetail.like("1")
                )
                .orderBy(item.price.desc())
                .fetch();

        for (Item item : result) {
            log.info("item={}",item);
        }
    }

    @Test
    @DisplayName("QueryDSL Test")
    void queryDSLTest2(){
        createItemList();

        String itemDetail="테스트";
        int price =10003;
        String itemSellStatus = "SELL";

        BooleanBuilder builder = new BooleanBuilder();

        builder.and(item.itemDetail.like("%"+ itemDetail +"%"));
        builder.and(item.price.gt(price));


        if (StringUtils.equals(itemSellStatus, ItemSellStatus.SELL)) {
            builder.and(item.status.eq(ItemSellStatus.SELL));
        }
        Pageable pageable = PageRequest.of(0, 5);

        Page<Item> result = repository.findAll(builder, pageable);
        log.info("전체 원소 갯수={}",result.getTotalElements());

        List<Item> content = result.getContent();
        for (Item item1 : content) {
            log.info("item1={}",item1);
        }
    }

    @Test
    @DisplayName("장바구니 회원 조회")
    void findByItemRegDateTest(){
        Member member = new Member();

    }

}
