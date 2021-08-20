package com.example.sbbank.controller.account;

import com.example.sbbank.payload.request.AccountRegisterRequestDto;
import com.example.sbbank.payload.request.TransferRequestDto;
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

    @PostMapping("/record")
    public String record(@RequestBody TransferRequestDto request,
                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        return accountService.record(request, userDetails.getMember());
    }
}
