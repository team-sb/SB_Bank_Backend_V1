package com.example.sbbank.controller;

import com.example.sbbank.payload.request.UpdateUserBalanceRequestDto;
import com.example.sbbank.payload.response.ShowUserAccountInfoResponseDto;
import com.example.sbbank.payload.response.ShowUserListResponseDto;
import com.example.sbbank.service.admin.AdminServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class AdminController {
    private final AdminServiceImpl adminService;

    @GetMapping("")
    public ShowUserListResponseDto showUsers() {
        return adminService.showUsers();
    }

    @GetMapping("/{user-id}")
    public ShowUserAccountInfoResponseDto showUserAccountInfo(@PathVariable(name = "user-id") Integer id) {
        return adminService.showUserAccountInfo(id);
    }

    @PatchMapping("/{user-id}/balance")
    public String updateUserBalance(@PathVariable(name = "user-id") Integer id,
                                    @RequestBody UpdateUserBalanceRequestDto request) {
        return adminService.updateUserBalance(request, id);
    }

}
