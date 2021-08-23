package com.example.sbbank.service.account;

import com.example.sbbank.entity.Transaction;
import com.example.sbbank.entity.account.Account;
import com.example.sbbank.entity.account.AccountRepository;
import com.example.sbbank.entity.account.Record;
import com.example.sbbank.entity.account.RecordRepository;
import com.example.sbbank.entity.member.Member;
import com.example.sbbank.exception.AccountNotFoundException;
import com.example.sbbank.exception.InvalidPasswordException;
import com.example.sbbank.exception.UserNotFoundException;
import com.example.sbbank.payload.request.AccountChargeRequestDto;
import com.example.sbbank.payload.request.AccountRegistrationRequestDto;
import com.example.sbbank.payload.request.AccountTransferRequestDto;
import com.example.sbbank.payload.response.AccountRegistrationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final RecordRepository recordRepository;
    private final AccountRepository accountRepository;

    public AccountRegistrationResponseDto register(AccountRegistrationRequestDto request, Member member) {
        if (Integer.compare(request.getSecPassword(), member.getSecPassword()) != 0) {
            throw new InvalidPasswordException();
        }

        Random rd = new Random();
        Integer rdAcc = rd.nextInt(999999999) + 111111111;

        Account account = Account.builder()
                .accountNumber(rdAcc)
                .balance(0)
                .member(member)
                .build();

        accountRepository.save(account);
        return new AccountRegistrationResponseDto(rdAcc);
    }

    public String transfer(AccountTransferRequestDto request, Member member) {
        if (Integer.compare(request.getSecPassword(), member.getSecPassword()) != 0) {
            throw new InvalidPasswordException();
        }

        Integer requestMoney = request.getMoney();
        Integer myBalance = member.getAccount().getBalance();

        Integer targetBalance = accountRepository.findByAccountNumber(request.getTarget())
                .map(account -> account.getBalance())
                .orElseThrow(AccountNotFoundException::new);
        Member targetId = accountRepository.findByAccountNumber(request.getTarget())
                .map(account -> account.getMember())
                .orElseThrow(AccountNotFoundException::new);

        Record sendRecord = Record.builder()
                .target(request.getTarget())
                .money(requestMoney)
                .transactionType(Transaction.RECEIVE)
                .transactionDate(new Date())
                .bfBalance(myBalance)
                .aftBalance(myBalance + requestMoney)
                .member(member)
                .build();

        Record receiveRecord = Record.builder()
                .target(member.getAccount().getAccountNumber())
                .money(-requestMoney)
                .transactionType(Transaction.SEND)
                .transactionDate(new Date())
                .bfBalance(targetBalance)
                .aftBalance(targetBalance - requestMoney)
                .member(targetId)
                .build();

        accountRepository.findByMemberId(member.getId())
                .map(account -> {
                    account.setBalance(myBalance + requestMoney);
                    accountRepository.save(account);
                    return account;
                })
                .orElseThrow(UserNotFoundException::new);

        accountRepository.findByAccountNumber(request.getTarget())
                .map(account -> {
                    account.setBalance(account.getBalance() - requestMoney);
                    accountRepository.save(account);
                    return account;
                })
                .orElseThrow(AccountNotFoundException::new);

        if(request.getMoney() < 0) {
            sendRecord.setTransactionType(Transaction.SEND);
            receiveRecord.setTransactionType(Transaction.RECEIVE);
        }

        recordRepository.save(sendRecord);
        recordRepository.save(receiveRecord);
        return "success record";
    }

    public String charge(AccountChargeRequestDto request, Member member) {
        if (Integer.compare(request.getSecPassword(), member.getSecPassword()) != 0) {
            throw new InvalidPasswordException();
        }

        Integer requestMoney = request.getMoney();

        if(requestMoney < 0) {
            accountRepository.findByMemberId(member.getId())
                    .map(account -> {
                        account.setBalance(account.getBalance() - requestMoney);
                        accountRepository.save(account);
                        return account;
                    })
                    .orElseThrow(UserNotFoundException::new);
        } else {
            accountRepository.findByMemberId(member.getId())
                    .map(account -> {
                        account.setBalance(account.getBalance() + requestMoney);
                        accountRepository.save(account);
                        return account;
                    })
                    .orElseThrow(UserNotFoundException::new);
        }
        return "success charge";
    }

}
