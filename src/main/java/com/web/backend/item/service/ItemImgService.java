package com.web.backend.item.service;

import com.web.backend.item.entity.ItemImg;
import com.web.backend.item.repository.ItemImgRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ItemImgService {

    @Value(value = "${uploadPath}")
    private String uploadPath;

    private final ItemImgRepository itemImgRepository;

    private final FileService fileService;

    public void saveItemImg(ItemImg itemImg, MultipartFile imgFile) throws IOException {
        String oriImgName = imgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        if(!StringUtils.isEmpty(oriImgName)) {
            imgName = fileService.uploadFile(uploadPath, oriImgName, imgFile.getBytes());
            log.info("uploadPath={}", uploadPath);
            imgUrl = "/images/item" + imgName;
        }

        itemImg.updateItemImg(oriImgName,imgName,imgUrl);
        itemImgRepository.save(itemImg);
    }
}
