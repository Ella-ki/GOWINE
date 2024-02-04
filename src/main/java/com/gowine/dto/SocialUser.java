package com.gowine.dto;

import com.gowine.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class SocialUser implements Serializable {
    private String name;
    private String email;
    //private String picture; // 추후 사용시 주석 해제

    public SocialUser(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
        //this.picture = member.getPicture();
    }
}
