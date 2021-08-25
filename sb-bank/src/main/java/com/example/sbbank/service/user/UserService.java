package com.example.sbbank.service.user;

import com.example.sbbank.entity.Transaction;
import com.example.sbbank.entity.account.AccountRepository;
import com.example.sbbank.entity.account.record.RecordRepository;
import com.example.sbbank.entity.member.Member;
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

    public UserBalanceResponseDto balance(Member member) {

        Integer balance = accountRepository.findByMemberId(member.getId())
                .map(account -> account.getBalance())
                .orElseThrow(AccountNotFoundException::new);

        return new UserBalanceResponseDto(balance);
    }

    public UserTransactionResponseDto everyTransaction(Member member) {
        List<UserTransactionResponseDto.everyTransaction> records = recordRepository.findByMemberId(member.getId())
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

    public UserTransactionResponseDto sendTransaction(Member member) {
        List<UserTransactionResponseDto.everyTransaction> records = recordRepository.findByMemberId(member.getId())
                .stream()
                .map(record -> new UserTransactionResponseDto.everyTransaction(
                        record.getMember().getId(),
                        record.getTarget(),
                        record.getMoney(),
                        record.getTransactionDate(),
                        record.getTransactionType(),
                        record.getBfBalance(),
                        record.getAftBalance()))
                .filter(transaction -> transaction.getTransactionType() == Transaction.SEND)
                .collect(Collectors.toList());

        return new UserTransactionResponseDto(records);
    }

    public UserTransactionResponseDto receiveTransaction(Member member) {
        List<UserTransactionResponseDto.everyTransaction> records = recordRepository.findByMemberId(member.getId())
                .stream()
                .map(record -> new UserTransactionResponseDto.everyTransaction(
                        record.getMember().getId(),
                        record.getTarget(),
                        record.getMoney(),
                        record.getTransactionDate(),
                        record.getTransactionType(),
                        record.getBfBalance(),
                        record.getAftBalance()))
                .filter(transaction -> transaction.getTransactionType() == Transaction.RECEIVE)
                .collect(Collectors.toList());

        return new UserTransactionResponseDto(records);
    }

}
