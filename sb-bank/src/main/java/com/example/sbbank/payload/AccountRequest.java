package com.example.sbbank.payload;

import com.example.sbbank.service.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {
    private Transaction transactionType;
    private LocalDateTime transactionTime;
}
