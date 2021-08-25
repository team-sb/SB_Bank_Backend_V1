package com.example.sbbank.service.user;

import com.example.sbbank.entity.member.Member;
import com.example.sbbank.payload.response.AccountBorrowResponseDto;
import com.example.sbbank.payload.response.UserBalanceResponseDto;
import com.example.sbbank.payload.response.UserTransactionResponseDto;

import java.util.List;

public interface UserService {
    UserBalanceResponseDto balance(Member member);
    UserTransactionResponseDto everyTransaction(Member member);
    UserTransactionResponseDto sendTransaction(Member member);
    UserTransactionResponseDto receiveTransaction(Member member);
    List<AccountBorrowResponseDto> borrow(Member member);
}
