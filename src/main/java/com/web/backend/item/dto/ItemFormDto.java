package com.web.backend.item.dto;

import com.web.backend.item.constrat.ItemSellStatus;
import com.web.backend.item.entity.Item;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ItemFormDto {
    private Long id;

    @NotBlank(message = "상품명은 항목 입니다.")
    private String itemName;

    @NotNull(message = "상품의 가격을 입력해주세요.")
    private int price;

    @NotNull(message = "상품의 재고 수량을 입력해주세요.")
    private int stock;

    private ItemSellStatus status;

    @NotBlank(message = "상품의 상세 설명은 필수입니다.")
    private String itemDetail;

    private List<ItemImgDto> itemImgList = new ArrayList<>();

    private List<Long> itemImgIdList = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    /**
     * 2가지 방향이 다 있어야함
     * entity -> dto
     * dto -> entity
     * @return
     */
    public Item createItem(){
        return modelMapper.map(this, Item.class);
        // 현재 Dto 객체를 Item 객체로 바꿔준다. dto -> entity
    }

    public static ItemFormDto of(Item item) {
        return modelMapper.map(item, ItemFormDto.class);
        // entity -> dto
    }

}
