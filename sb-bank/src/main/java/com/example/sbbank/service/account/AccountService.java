package com.example.sbbank.service.account;

import com.example.sbbank.entity.account.Account;
import com.example.sbbank.entity.account.AccountRepository;
import com.example.sbbank.payload.AccountRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public String register(AccountRequest request) {
        accountRepository.save(
                Account.builder()
                .transactionType(request.getTransactionType())
                .transactionDate(new Date())
                .build()
        );

        return "success register";
    }
}
