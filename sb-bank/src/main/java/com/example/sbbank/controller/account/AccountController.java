package com.example.sbbank.controller.account;

import com.example.sbbank.payload.AccountRequest;
import com.example.sbbank.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/register")
    public String register(@RequestBody AccountRequest request) {
        return accountService.register(request);
    }
}
