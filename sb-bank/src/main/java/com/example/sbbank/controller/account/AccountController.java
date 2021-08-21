package com.example.sbbank.controller.account;

import com.example.sbbank.payload.request.AccountChargeRequestDto;
import com.example.sbbank.payload.request.AccountRegisterRequestDto;
import com.example.sbbank.payload.request.AccountTransferRequestDto;
import com.example.sbbank.payload.response.AccountRegisterResponseDto;
import com.example.sbbank.security.auth.CustomUserDetails;
import com.example.sbbank.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/register")
    public AccountRegisterResponseDto register(@RequestBody AccountRegisterRequestDto requestDto,
                                               @AuthenticationPrincipal CustomUserDetails userDetails) {
        return accountService.register(requestDto, userDetails.getMember());
    }

    @PostMapping("/transfer")
    public String transfer(@RequestBody AccountTransferRequestDto request,
                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        return accountService.transfer(request, userDetails.getMember());
    }

    @PostMapping("/charge")
    public String charge(@RequestBody AccountChargeRequestDto request,
                         @AuthenticationPrincipal CustomUserDetails userDetails) {
        return accountService.charge(request, userDetails.getMember());
    }

}
