package com.example.sbbank.service.account;

import com.example.sbbank.entity.Transaction;
import com.example.sbbank.entity.account.Account;
import com.example.sbbank.entity.account.AccountRepository;
import com.example.sbbank.entity.account.Record;
import com.example.sbbank.entity.account.RecordRepository;
import com.example.sbbank.entity.member.Member;
import com.example.sbbank.payload.request.AccountRegisterRequestDto;
import com.example.sbbank.payload.request.TransferRequestDto;
import com.example.sbbank.payload.response.AccountRegisterResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final RecordRepository recordRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountRegisterResponseDto register(AccountRegisterRequestDto request, Member member) {

        if(!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException();
        }

        Random rd = new Random();
        Integer rdAcc = rd.nextInt(999999999) + 111111111;

        Account account = Account.builder()
                .account(rdAcc)
                .balance(1000)
                .member(member)
                .build();

        accountRepository.save(account);

        return new AccountRegisterResponseDto(rdAcc);
    }

    public String record(TransferRequestDto request, Member member) {

        Record record = Record.builder()
                .target(request.getTarget())
                .money(request.getMoney())
                .transactionType(Transaction.RECEIVE)
                .transactionDate(new Date())
                .balance(0) // 0 아님
                .member(member)
                .build();

        if(request.getMoney() < 0) {
            Transaction type = Transaction.SEND;
            record.setTransactionType(type);
        }

        recordRepository.save(record);

        return "success record";
    }
}
