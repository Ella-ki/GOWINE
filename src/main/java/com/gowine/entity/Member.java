package com.gowine.entity;

import com.gowine.config.BooleanToYNConverter;
import com.gowine.constant.Role;
import com.gowine.dto.Address;
import com.gowine.dto.MemberFormDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "member")
@Data
@NoArgsConstructor
public class Member extends BaseEntity {
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String email;

    private String name;

    private String password;

    private String passwordConfirm;

    @Embedded
    private Address address;

    private String tel;

   // private String profileImg; // 추후 사용시 주석 해제

    private String provider;

    private String providerId;

    @Convert(converter = BooleanToYNConverter.class)
    private boolean useAgree; // 이용약관 동의

    @Convert(converter = BooleanToYNConverter.class)
    private boolean privacyAgree; // 개인정보 동의

    @Convert(converter = BooleanToYNConverter.class)
    private boolean infoAgree; // 쇼핑정보 수신동의

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = true)
    private int liked;

    // 일반 유저
    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        Address address = new Address(memberFormDto.getZipcode(), memberFormDto.getAddr(), memberFormDto.getAddrDetail());
        member.setEmail(memberFormDto.getEmail());
        member.setName(memberFormDto.getName());
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        String passwordConfirm = passwordEncoder.encode(memberFormDto.getPasswordConfirm());
        member.setPassword(password);
        member.setPasswordConfirm(passwordConfirm);
        member.setAddress(address);
        member.setTel(memberFormDto.getTel());
        member.setPrivacyAgree(memberFormDto.isPrivacyAgree());
        member.setUseAgree(memberFormDto.isUseAgree());
        member.setInfoAgree(memberFormDto.isInfoAgree());
        member.setRole(Role.USER);
        return member;
    }

    // 생성자 패턴
    // 소셜가입 유저
    public Member(String name, String email, String provider, String providerId, Role role) {
        this.name = name;
        this.email = email;
        this.provider = provider;
        this.providerId = providerId;
        this.role = role;
    }

    public Member update(String name){
        this.name = name;
        return this;
    }

}
