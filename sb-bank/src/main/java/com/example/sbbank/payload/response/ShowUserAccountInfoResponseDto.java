package com.example.sbbank.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShowUserAccountInfoResponseDto {
    private String name;
    private Integer accountNumber;
    private Integer balance;
}
