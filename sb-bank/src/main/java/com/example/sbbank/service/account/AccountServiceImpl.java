package com.example.sbbank.service.account;

import com.example.sbbank.entity.Authority;
import com.example.sbbank.entity.Transaction;
import com.example.sbbank.entity.account.Account;
import com.example.sbbank.entity.account.AccountRepository;
import com.example.sbbank.entity.account.record.Transfer.TransferRecord;
import com.example.sbbank.entity.account.record.Transfer.TransferRecordRepository;
import com.example.sbbank.entity.account.record.loan.LoanRecord;
import com.example.sbbank.entity.account.record.loan.LoanRepository;
import com.example.sbbank.entity.member.Member;
import com.example.sbbank.entity.member.MemberRepository;
import com.example.sbbank.exception.AccountAlreadyExistsException;
import com.example.sbbank.exception.AccountNotFoundException;
import com.example.sbbank.exception.UserNotFoundException;
import com.example.sbbank.payload.request.AccountChargeRequestDto;
import com.example.sbbank.payload.request.AccountTransferRequestDto;
import com.example.sbbank.payload.response.AccountBorrowResponseDto;
import com.example.sbbank.payload.response.AccountRegistrationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{

    private final TransferRecordRepository transferRecordRepository;
    private final AccountRepository accountRepository;
    private final MemberRepository memberRepository;
    private final LoanRepository loanRepository;

    @Override
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

    @Override
    public String transfer(AccountTransferRequestDto request, Member member) {

        Integer requestMoney = request.getMoney();
        Integer myBalance = member.getAccount().getBalance();

        Integer targetBalance = accountRepository.findByAccountNumber(request.getTarget())
                .map(account -> account.getBalance())
                .orElseThrow(AccountNotFoundException::new);
        Member targetId = accountRepository.findByAccountNumber(request.getTarget())
                .map(account -> account.getMember())
                .orElseThrow(AccountNotFoundException::new);

        TransferRecord sendTransferRecord = TransferRecord.builder()
                .target(request.getTarget())
                .money(requestMoney)
                .transactionType(Transaction.RECEIVE)
                .transactionDate(new Date())
                .bfBalance(myBalance)
                .aftBalance(myBalance + requestMoney)
                .member(member)
                .build();

        TransferRecord receiveTransferRecord = TransferRecord.builder()
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
            sendTransferRecord.setTransactionType(Transaction.SEND);
            receiveTransferRecord.setTransactionType(Transaction.RECEIVE);
        }

        setAuthority(member);
        transferRecordRepository.save(sendTransferRecord);
        transferRecordRepository.save(receiveTransferRecord);

        return "success record";
    }

    @Override
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

    @Override
    public AccountBorrowResponseDto borrow(AccountChargeRequestDto request, Member member) {

        Double interest = request.getMoney() * 0.001;

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 3);
        Date expirationDate = cal.getTime();

        LoanRecord loanRecord = LoanRecord.builder()
                .money(request.getMoney())
                .interest(interest)
                .loanExpirationDate(expirationDate)
                .member(member)
                .build();

        accountRepository.findByMemberId(member.getId())
                .map(account -> {
                    account.setBalance(account.getBalance() + request.getMoney());
                    accountRepository.save(account);
                    return account;
                })
                .orElseThrow();

        loanRepository.save(loanRecord);

        return AccountBorrowResponseDto.builder()
                .money(loanRecord.getMoney())
                .interest(loanRecord.getInterest())
                .loanExpirationDate(loanRecord.getLoanExpirationDate())
                .build();

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
