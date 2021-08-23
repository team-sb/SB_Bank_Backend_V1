package com.example.sbbank.payload.request;

import com.example.sbbank.entity.Authority;
import com.example.sbbank.entity.member.Member;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberJoinRequestDto {
    private String name;
    private String username;

    @Setter
    private String password;

    @JsonProperty("sec_password")
    private Integer secPassword;

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .username(username)
                .password(password)
                .secPassword(secPassword)
                .authority(Authority.ROLE_USER)
                .build();
    }
}
