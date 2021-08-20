package com.example.sbbank.controller.auth;

import com.example.sbbank.payload.request.MemberLoginRequestDto;
import com.example.sbbank.payload.request.MemberJoinRequestDto;
import com.example.sbbank.payload.response.TokenResponseDto;
import com.example.sbbank.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth/join")
    public String join(@RequestBody MemberJoinRequestDto request) {
        return authService.join(request);
    }

    @PostMapping("/auth/login")
    public TokenResponseDto login(@RequestBody MemberLoginRequestDto request) {
        return authService.login(request);
    }

}
