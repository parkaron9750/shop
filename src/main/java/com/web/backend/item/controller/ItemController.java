package com.web.backend.item.controller;

import com.web.backend.item.dto.ItemFormDto;
import com.web.backend.item.service.ItemService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/admin/item/new")
    public String itemForm(Model model) {
        model.addAttribute("itemFormDto", new ItemFormDto());
        return "item/itemForm";
    }

    @PostMapping("/admin/item/new")
    public String createItem(@Valid ItemFormDto dto,
                             @RequestParam("itemImgFile")List<MultipartFile> fileList,
                             BindingResult bindingResult,
                             Model model) {

        if (bindingResult.hasErrors()) {
            return "item/itemForm";
        }
        if(fileList.get(0).isEmpty() && dto.getId() == null) {
            model.addAttribute("errorMessage", "첫번 쨰 상품 이미지는 필수입니다.");
            return "item/itemForm";
        }

        try {
            itemService.saveItem(dto,fileList);
        } catch (IOException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "item/itemForm";
        }
        return "redirect:/";

    }

    @GetMapping("/admin/item/{itemId}")
    public String itemDetail(@PathVariable("itemId")Long id, Model model) {

        try{
            ItemFormDto dto = itemService.itemDetail(id);
            model.addAttribute("itemFormDto", dto);
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "존재하지 않는 상품입니다.");
            model.addAttribute("itemFormDto", new ItemFormDto());
            return "item/itemForm";
        }
        return "item/itemForm";
    }

    @PostMapping("/admin/item/{itemId}")
    public String itemUpdate(@Valid ItemFormDto dto, //검증
                             BindingResult bindingResult, //오류
                             @RequestParam("itemImgFile")List<MultipartFile> imgFile, //매개변수 값
                             Model model) {

        if (bindingResult.hasErrors()) {
            return "item/itemForm";
        }
        if(imgFile.get(0).isEmpty() && dto.getId() == null) {
            model.addAttribute("errorMessage", "첫번 째 이미지는 필수 항목입니다.");
            return "item/itemForm";
        }

        try {
            itemService.updateItem(dto,imgFile);
        } catch (IOException e) {
            model.addAttribute("errorMessage", "상품 수정하는데 실패하였습니다. 다시 확인해주세요.");
            return "item/itemForm";
        }

        return "redirect:/";

    }

}
