package com.web.backend.item.service;

import com.web.backend.item.dto.ItemFormDto;
import com.web.backend.item.dto.ItemImgDto;
import com.web.backend.item.entity.Item;
import com.web.backend.item.entity.ItemImg;
import com.web.backend.item.repository.ItemImgRepository;
import com.web.backend.item.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;

    public Long saveItem(ItemFormDto dto, List<MultipartFile> itemImgList) throws IOException {
        Item item = dto.createItem(); // dto -> entity
        itemRepository.save(item);

        for (int i = 0; i < itemImgList.size(); i++) {
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);
            if(i == 0){
                itemImg.setImgMain("Y"); // 대표이미지 등록 여부
            } else {
                itemImg.setImgMain("N"); // 대표이미지 등록 여부
            }
            itemImgService.saveItemImg(itemImg, itemImgList.get(i)); //이미지를 등록한 것 만큼 저장한다.
        }
        return item.getId();
    }

    public ItemFormDto itemDetail(Long itemId) {
        List<ItemImg> entityImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
        List<ItemImgDto> dtoImgList = new ArrayList<>();

        //entity -> dto List형으로 바꿔준다.
        for (ItemImg itemImg : entityImgList) {
            ItemImgDto dto = ItemImgDto.of(itemImg);
            dtoImgList.add(dto);
        }
        Item item = itemRepository.findById(itemId).orElseThrow(()-> new RuntimeException("상품이 존재하지 않습니다."));
        ItemFormDto formDto = ItemFormDto.of(item);
        //item -> List형으로 붙여서 넘겨준다.
        formDto.setItemImgList(dtoImgList);
        return formDto;
    }

    public Long updateItem(ItemFormDto dto, List<MultipartFile> itemImgList) throws IOException {

        Item item = itemRepository.findById(dto.getId()).orElseThrow(EntityNotFoundException::new);
        item.updateItem(dto);

        List<Long> itemImgIds = dto.getItemImgIdList();
        for (int i = 0; i < itemImgIds.size(); i++) {
            itemImgService.updateItemImg(itemImgIds.get(i), itemImgList.get(i));
        }

        return item.getId();
    }

}
