package com.web.backend.member.entity;

import com.web.backend.member.constart.Role;
import com.web.backend.member.dto.MemberDto;
import com.web.backend.utils.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_name")
    private String name;

    @Column(name = "member_email", unique = true)
    private String email;

    @Column(name = "member_password")
    private String password;

    @Column(name = "member_address")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_role")
    private Role role;

    public static Member createMember(MemberDto dto, PasswordEncoder encoder) {
        Member member = new Member();
        member.setName(dto.getName());
        member.setEmail(dto.getEmail());
        member.setAddress(dto.getAddress());
        member.setRole(Role.USER);
        member.setPassword(encoder.encode(dto.getPassword()));
        return member;
    }
}
