package com.example.sbbank.service.user;

import com.example.sbbank.entity.account.AccountRepository;
import com.example.sbbank.entity.account.Record;
import com.example.sbbank.entity.account.RecordRepository;
import com.example.sbbank.exception.AccountNotFoundException;
import com.example.sbbank.payload.response.UserBalanceResponseDto;
import com.example.sbbank.payload.response.UserTransactionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final AccountRepository accountRepository;
    private final RecordRepository recordRepository;

    public UserBalanceResponseDto balance(Integer id) {

        Integer balance = accountRepository.findByMemberId(id)
                .map(account -> account.getBalance())
                .orElseThrow(AccountNotFoundException::new);

        return new UserBalanceResponseDto(balance);
    }

    public UserTransactionResponseDto everyTransaction(Integer id) {
        List<UserTransactionResponseDto.everyTransaction> records = recordRepository.findByMemberId(id)
                .stream()
                .map(record -> new UserTransactionResponseDto.everyTransaction(
                        record.getMember().getId(),
                        record.getTarget(),
                        record.getMoney(),
                        record.getTransactionDate(),
                        record.getTransactionType(),
                        record.getBfBalance(),
                        record.getAftBalance()))
                .collect(Collectors.toList());

        return new UserTransactionResponseDto(records);
    }

}
