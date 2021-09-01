package com.example.sbbank.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserBalanceResponseDto {
    private String name;
    private Integer accountNumber;
    private Integer balance;
}
