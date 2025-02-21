package com.web.backend.member.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private String name;
    private String email;
    private String password;
    private String address;
}
