package com.web.backend.item;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "item")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    //상품 이름
    @Column(name = "item_name" ,length = 50 ,nullable = false)
    private String itemName;

    //상품 가격
    @Column(name = "item_price", nullable = false)
    private int price;

    //상품 재고수량
    @Column(name = "item_stock", nullable = false)
    private int stock;

    //상품 상태
    @Column(name = "item_status")
    private ItemSellStatus status;

    //상품 상세내용
    @Lob
    @Column(name = "item_detail", nullable = false)
    private String itemDetail;

    //상품 등록시간
    @Column(name = "item_regdate")
    private LocalDateTime itemRegDate;

    //상품 수정시간
    @Column(name = "item_up time")
    private LocalDateTime updateTime;
}
