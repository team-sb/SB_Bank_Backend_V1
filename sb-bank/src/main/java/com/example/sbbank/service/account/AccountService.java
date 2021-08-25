package com.example.sbbank.service.account;

import com.example.sbbank.entity.Authority;
import com.example.sbbank.entity.Transaction;
import com.example.sbbank.entity.account.Account;
import com.example.sbbank.entity.account.AccountRepository;
import com.example.sbbank.entity.account.record.Record;
import com.example.sbbank.entity.account.record.RecordRepository;
import com.example.sbbank.entity.member.Member;
import com.example.sbbank.entity.member.MemberRepository;
import com.example.sbbank.exception.AccountAlreadyExistsException;
import com.example.sbbank.exception.AccountNotFoundException;
import com.example.sbbank.exception.UserNotFoundException;
import com.example.sbbank.payload.request.AccountChargeRequestDto;
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
    private final MemberRepository memberRepository;

    public AccountRegistrationResponseDto register(Member member) {
        if(accountRepository.existsByMemberId(member.getId())) {
            throw new AccountAlreadyExistsException();
        }

        Integer rdAcc = new Random().nextInt(999999999) + 111111111;

        Account account = Account.builder()
                .accountNumber(rdAcc)
                .balance(0)
                .member(member)
                .build();

        setAuthority(member);
        accountRepository.save(account);
        return new AccountRegistrationResponseDto(rdAcc);
    }

    public String transfer(AccountTransferRequestDto request, Member member) {
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
                .orElseThrow(AccountNotFoundException::new);

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

        setAuthority(member);
        recordRepository.save(sendRecord);
        recordRepository.save(receiveRecord);
        return "success record";
    }

    public String charge(AccountChargeRequestDto request, Member member) {
        Integer requestMoney = request.getMoney();

        if(requestMoney < 0) {
            accountRepository.findByMemberId(member.getId())
                    .map(account -> {
                        account.setBalance(account.getBalance() - requestMoney);
                        accountRepository.save(account);
                        return account;
                    })
                    .orElseThrow(AccountNotFoundException::new);
        } else {
            accountRepository.findByMemberId(member.getId())
                    .map(account -> {
                        account.setBalance(account.getBalance() + requestMoney);
                        accountRepository.save(account);
                        return account;
                    })
                    .orElseThrow(AccountNotFoundException::new);
        }

        setAuthority(member);
        return "success charge";
    }

    public Member setAuthority(Member member) {
        return memberRepository.findById(member.getId())
                .map(member1 -> {
                    member1.setAuthority(Authority.ROLE_USER);
                    memberRepository.save(member1);
                    return member1;
                })
                .orElseThrow(UserNotFoundException::new);
    }

}
