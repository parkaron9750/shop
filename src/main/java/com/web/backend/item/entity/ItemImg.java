package com.web.backend.item.entity;

import com.web.backend.utils.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class ItemImg extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_img_id")
    private Long id;

    @Column(name = "img_name")
    private String imgName; //이미지 파일명

    @Column(name = "ori_img_name")
    private String oriImgName; //원본 이미지 파일명

    @Column(name = "img_url")
    private String imgUrl; // 이미지 조회 경로

    @Column(name = "img_main")
    private String imgMain; // 대표 이미지 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public void updateItemImg(String oriImgName, String imgName, String imgUrl) {
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }
}
