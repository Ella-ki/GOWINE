package com.gowine.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class MemberFormDto {
    @NotBlank
    //@Email(regexp="/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i")
    @Email
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d|\\W)(?!.*\\s).{8,20}$")
    private String password;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d|\\W)(?!.*\\s).{8,20}$")
    private String passwordConfirm;

    private String zipcode;

    private String addr;

    private String addrDetail;

    //@Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$")
    private String tel;

    @NotNull
    private boolean useAgree; // 이용약관 동의

    @NotNull
    private boolean privacyAgree; // 개인정보 동의

    @NotNull
    private boolean infoAgree; // 쇼핑정보 수신동의

}
