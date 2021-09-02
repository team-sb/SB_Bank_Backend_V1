package com.example.sbbank.service.user;

import com.example.sbbank.entity.Transaction;
import com.example.sbbank.entity.account.AccountRepository;
import com.example.sbbank.entity.account.record.transfer.TransferRecordRepository;
import com.example.sbbank.entity.account.record.loan.LoanRepository;
import com.example.sbbank.entity.member.Member;
import com.example.sbbank.exception.AccountNotFoundException;
import com.example.sbbank.payload.response.UserShowLoanResponseDto;
import com.example.sbbank.payload.response.UserBalanceResponseDto;
import com.example.sbbank.payload.response.UserTransactionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AccountRepository accountRepository;
    private final TransferRecordRepository transferRecordRepository;
    private final LoanRepository loanRepository;

    @Override
    @Transactional(readOnly = true)
    public UserBalanceResponseDto balance(Member member) {

        Integer balance = accountRepository.findByMemberId(member.getId())
                .map(account -> account.getBalance())
                .orElseThrow(AccountNotFoundException::new);

        return new UserBalanceResponseDto(member.getName(), member.getAccount().getAccountNumber(), balance);
    }

    @Override
    @Transactional(readOnly = true)
    public UserTransactionResponseDto everyTransaction(Member member) {

        List<UserTransactionResponseDto.everyTransaction> records = transferRecordRepository.findByMemberId(member.getId())
                .stream()
                .map(record -> new UserTransactionResponseDto.everyTransaction(
                        record.getMember().getId(),
                        record.getTargetAccount(),
                        record.getTargetName(),
                        record.getMoney(),
                        record.getTransactionDate(),
                        record.getTransactionType(),
                        record.getBfBalance(),
                        record.getAftBalance()))
                .collect(Collectors.toList());

        return new UserTransactionResponseDto(records);
    }

    @Override
    @Transactional(readOnly = true)
    public UserTransactionResponseDto sendTransaction(Member member) {

        List<UserTransactionResponseDto.everyTransaction> records = transferRecordRepository.findByMemberId(member.getId())
                .stream()
                .map(record -> new UserTransactionResponseDto.everyTransaction(
                        record.getMember().getId(),
                        record.getTargetAccount(),
                        record.getTargetName(),
                        record.getMoney(),
                        record.getTransactionDate(),
                        record.getTransactionType(),
                        record.getBfBalance(),
                        record.getAftBalance()))
                .filter(transaction -> transaction.getTransactionType() == Transaction.SEND)
                .collect(Collectors.toList());

        return new UserTransactionResponseDto(records);
    }

    @Override
    @Transactional(readOnly = true)
    public UserTransactionResponseDto receiveTransaction(Member member) {

        List<UserTransactionResponseDto.everyTransaction> records = transferRecordRepository.findByMemberId(member.getId())
                .stream()
                .map(record -> new UserTransactionResponseDto.everyTransaction(
                        record.getMember().getId(),
                        record.getTargetAccount(),
                        record.getTargetName(),
                        record.getMoney(),
                        record.getTransactionDate(),
                        record.getTransactionType(),
                        record.getBfBalance(),
                        record.getAftBalance()))
                .filter(transaction -> transaction.getTransactionType() == Transaction.RECEIVE)
                .collect(Collectors.toList());

        return new UserTransactionResponseDto(records);
    }

    @Override
    @Transactional(readOnly = true)
    public UserShowLoanResponseDto borrow(Member member) {

        List<UserShowLoanResponseDto.loan> loans = loanRepository.findByMemberId(member.getId())
                .stream()
                .map(loanRecord -> new UserShowLoanResponseDto.loan(
                        loanRecord.getMoney(),
                        loanRecord.getInterest(),
                        loanRecord.getBorrowedDate(),
                        loanRecord.getLoanExpirationDate()))
                .collect(Collectors.toList());

        return new UserShowLoanResponseDto(loans);
    }

}
