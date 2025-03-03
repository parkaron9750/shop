package com.web.backend.item.service;

import com.web.backend.item.entity.ItemImg;
import com.web.backend.item.repository.ItemImgRepository;
import jakarta.persistence.EntityNotFoundException;
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

    /**
     * 개별적으로 파일 하나하나 저장
     * @param itemImg
     * @param imgFile
     * @throws IOException
     */
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

    public void updateItemImg(Long itemImgId, MultipartFile imgFile) throws IOException {
        if(!imgFile.isEmpty()) {
            //entity에서 img를 가져온다.
            ItemImg itemImg = itemImgRepository.findById(itemImgId).orElseThrow(EntityNotFoundException::new);

            //entity에서 가져온 기존 이미지 이름이 있을때 파일을 삭제한다.
            if(!StringUtils.isEmpty(itemImg.getImgName())) {
                fileService.deleteFile(uploadPath + "/" + itemImg.getImgName());
            }

            //파일을 새로운 파일로 업데이트한다.
            String oriImgName = imgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(uploadPath, oriImgName, imgFile.getBytes());
            String imgUrl = "/images/item" + itemImg.getImgName();

            //이미지를 저장한다.
            itemImg.updateItemImg(oriImgName,imgName,imgUrl);
        }
    }
}
