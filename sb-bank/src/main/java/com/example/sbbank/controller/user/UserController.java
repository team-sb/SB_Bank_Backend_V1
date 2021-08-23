package com.example.sbbank.controller.user;

import com.example.sbbank.payload.response.BalanceResponseDto;
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
    public BalanceResponseDto balance(@PathVariable(name = "user-id") Integer id) {
        return userService.balance(id);
    }

}
