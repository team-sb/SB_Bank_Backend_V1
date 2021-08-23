package com.example.sbbank.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberJoinRequestDto {
    private String name;
    private String username;

    @Size(min = 6, max = 12)
    private String password;

    @Size(min = 4, max = 4)
    @JsonProperty("sec_password")
    private String secPassword;

}
