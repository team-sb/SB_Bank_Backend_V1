package com.example.sbbank.payload.response;

import com.example.sbbank.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserTransactionResponseDto {
    private List<everyTransaction> transactions;

    @Getter
    @AllArgsConstructor
    public static class everyTransaction {
        private Integer memberId;
        private Integer targetAccount;
        private String targetName;
        private Integer money;
        private Date transactionDate;
        private Transaction transactionType;
        private Integer bfBalance;
        private Integer aftBalance;
    }
}
