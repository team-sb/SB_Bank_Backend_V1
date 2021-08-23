package com.example.sbbank.controller.user;

import com.example.sbbank.payload.response.UserBalanceResponseDto;
import com.example.sbbank.payload.response.UserTransactionResponseDto;
import com.example.sbbank.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/balance/{user-id}")
    public UserBalanceResponseDto balance(@PathVariable(name = "user-id") Integer id) {
        return userService.balance(id);
    }

    @GetMapping("/transaction/{user-id}")
    public UserTransactionResponseDto everyTransaction(@PathVariable(name = "user-id") Integer id) {
        return userService.everyTransaction(id);
    }

    @GetMapping("/transaction/send/{user-id}")
    public UserTransactionResponseDto sendTransaction(@PathVariable(name = "user-id") Integer id) {
        return userService.sendTransaction(id);
    }

}
