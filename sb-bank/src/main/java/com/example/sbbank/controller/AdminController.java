package com.example.sbbank.controller;

import com.example.sbbank.payload.response.UserListResponseDto;
import com.example.sbbank.service.admin.AdminServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminServiceImpl adminService;

    @GetMapping("/users")
    public UserListResponseDto users() {
        return adminService.users();
    }

}
