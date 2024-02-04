package com.gowine.config;

import com.gowine.constant.Role;
import com.gowine.entity.Member;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@Data
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    //private String picture;
    private String provider;
    private String providerId;
    private Role role;

    public OAuthAttributes(Map<String, Object> attributes,
                           String nameAttributeKey,
                           String name,
                           String email,
                           String provider,
                           String providerId,
                           Role role) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.provider = provider;
        this.providerId = providerId;
        this.role = role;
    }

    public OAuthAttributes() {
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if (registrationId.equals("kakao")) {
            return ofKakao(registrationId, userNameAttributeName, attributes);
        } else if(registrationId.equals("google")) {
            return ofGoogle(registrationId,userNameAttributeName, attributes);
        }
        return ofNaver(registrationId,userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofKakao(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> kakao_account = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakao_account.get("profile");
        String providerId = attributes.get("id").toString();
        Role role = Role.USER;
        return new OAuthAttributes(attributes, userNameAttributeName, (String) profile.get("nickname"), (String) profile.get("email"), registrationId, providerId,role);
    }
    private static OAuthAttributes ofGoogle(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        Role role = Role.USER;
        return new OAuthAttributes(attributes, userNameAttributeName, (String) attributes.get("name"), (String) attributes.get("email"),registrationId, (String) attributes.get("sub"),role);
    }

    private static OAuthAttributes ofNaver(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        Role role = Role.USER;
        return new OAuthAttributes(attributes, userNameAttributeName, (String) response.get("name"), (String) response.get("email"),registrationId, (String) response.get("id"),role);
    }

    public Member toEntity() {
        return new Member(name, email, provider, providerId, role);
    }

}