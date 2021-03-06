package com.example.sbbank.controller;

import com.example.sbbank.payload.response.UserShowLoanResponseDto;
import com.example.sbbank.payload.response.UserBalanceResponseDto;
import com.example.sbbank.payload.response.UserTransactionResponseDto;
import com.example.sbbank.security.auth.CustomUserDetails;
import com.example.sbbank.service.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserServiceImpl userServiceImpl;

    @GetMapping("/balance")
    public UserBalanceResponseDto balance(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return userServiceImpl.balance(userDetails.getMember());
    }

    @GetMapping("/transaction")
    public UserTransactionResponseDto everyTransaction(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return userServiceImpl.everyTransaction(userDetails.getMember());
    }

    @GetMapping("/transaction/send")
    public UserTransactionResponseDto sendTransaction(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return userServiceImpl.sendTransaction(userDetails.getMember());
    }

    @GetMapping("/transaction/receive")
    public UserTransactionResponseDto receiveTransaction(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return userServiceImpl.receiveTransaction(userDetails.getMember());
    }

    @GetMapping("/loan")
    public UserShowLoanResponseDto borrow(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return userServiceImpl.borrow(userDetails.getMember());
    }

}
