package com.web.backend.dto;

import com.web.backend.item.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ItemDto {

    private Long id;
    private String itemName;
    private int price;
    private ItemSellStatus status;
    private String itemDetail;
    private LocalDateTime itemRegDate;
    private LocalDateTime updateTime;

}
