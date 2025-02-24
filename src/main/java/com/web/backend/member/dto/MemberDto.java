package com.web.backend.member.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

    @NotBlank(message = "이름은 필수 항목입니다.")
    private String name;

    @NotEmpty(message = "이메일은 필수 항목입니다.")
    @Email(message = "올바르지 않은 이메일 형식입니다.")
    private String email;

    @NotEmpty(message = "비밀번호는 필수 항목입니다.")
    @Length(min = 4,max = 12,message = "비밀번호는 최소 4글자 최대 12글자 안에서 입력해주세요.")
    private String password;

    @NotEmpty(message = "주소는 필수 항목입니다.")
    private String address;
}
