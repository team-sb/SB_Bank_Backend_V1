package com.example.sbbank.service.user;

import com.example.sbbank.entity.account.Account;
import com.example.sbbank.entity.account.AccountRepository;
import com.example.sbbank.entity.member.Member;
import com.example.sbbank.exception.AccountNotFoundException;
import com.example.sbbank.payload.response.BalanceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final AccountRepository accountRepository;

    public BalanceResponseDto balance(Integer id) {

        Integer balance = accountRepository.findByMemberId(id)
                .map(account -> account.getBalance())
                .orElseThrow(AccountNotFoundException::new);
        return new BalanceResponseDto(balance);
    }

}
