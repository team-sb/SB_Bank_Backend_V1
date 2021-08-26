package com.example.sbbank.service.admin;

import com.example.sbbank.payload.request.UpdateUserBalanceRequestDto;
import com.example.sbbank.payload.response.ShowUserAccountInfoResponseDto;
import com.example.sbbank.payload.response.ShowUserListResponseDto;

public interface AdminService {
    ShowUserListResponseDto showUsers();
    ShowUserAccountInfoResponseDto showUserAccountInfo(Integer id);
    String updateUserBalance(UpdateUserBalanceRequestDto request, Integer id);
}
