package com.example.sbbank.controller;

import com.example.sbbank.payload.request.AccountChargeRequestDto;
import com.example.sbbank.payload.request.AccountTransferRequestDto;
import com.example.sbbank.payload.response.AccountChargeLoanResponseDto;
import com.example.sbbank.payload.response.AccountRegistrationResponseDto;
import com.example.sbbank.security.auth.CustomUserDetails;
import com.example.sbbank.service.account.AccountServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private final AccountServiceImpl accountServiceImpl;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountRegistrationResponseDto register(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return accountServiceImpl.register(userDetails.getMember());
    }

    @PostMapping("/transfer")
    public String transfer(@RequestBody AccountTransferRequestDto request,
                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        return accountServiceImpl.transfer(request, userDetails.getMember());
    }

    @PostMapping("/charge")
    public String charge(@RequestBody AccountChargeRequestDto request,
                         @AuthenticationPrincipal CustomUserDetails userDetails) {
        return accountServiceImpl.charge(request, userDetails.getMember());
    }

    @PostMapping("/loan")
    public AccountChargeLoanResponseDto borrow(@RequestBody AccountChargeRequestDto request,
                                               @AuthenticationPrincipal CustomUserDetails userDetails) {
        return accountServiceImpl.borrow(request, userDetails.getMember());
    }

}
