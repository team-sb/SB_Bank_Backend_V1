package com.example.sbbank.service.account;

import com.example.sbbank.entity.member.Member;
import com.example.sbbank.payload.request.AccountChargeRequestDto;
import com.example.sbbank.payload.request.AccountTransferRequestDto;
import com.example.sbbank.payload.response.AccountBorrowResponseDto;
import com.example.sbbank.payload.response.AccountRegistrationResponseDto;

public interface AccountService {
    AccountRegistrationResponseDto register(Member member);
    String transfer(AccountTransferRequestDto request, Member member);
    String charge(AccountChargeRequestDto request, Member member);
    AccountBorrowResponseDto borrow(AccountChargeRequestDto request, Member member);
}
