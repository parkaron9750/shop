package com.web.backend.cart.entity;

import com.web.backend.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;


    @OneToOne(fetch = FetchType.LAZY) // 1:1 방향
    @JoinColumn(name = "member_id") //Join 되는 컬럼 이름
    private Member member;
}
