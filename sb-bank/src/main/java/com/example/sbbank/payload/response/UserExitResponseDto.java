package com.example.sbbank.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserExitResponseDto {
    private Integer id;
    private String name;
    private String username;
    private Integer accountNumber;
}
