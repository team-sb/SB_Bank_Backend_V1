package com.example.sbbank.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountBorrowRequestDto {
    private Integer money;
    private Integer date;
}
