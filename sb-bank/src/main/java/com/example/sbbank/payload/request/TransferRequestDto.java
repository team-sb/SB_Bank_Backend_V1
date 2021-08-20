package com.example.sbbank.payload;

import com.example.sbbank.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {
    private Integer target;
    private Integer money;
    private Transaction type;
    private Integer balance;
}
