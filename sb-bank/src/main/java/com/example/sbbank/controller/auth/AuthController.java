package com.example.sbbank.controller.auth;

import com.example.sbbank.payload.request.MemberLoginRequestDto;
import com.example.sbbank.payload.request.MemberJoinRequestDto;
import com.example.sbbank.payload.response.TokenResponseDto;
import com.example.sbbank.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.CREATED)
    public String join(@Valid @RequestBody MemberJoinRequestDto request) {
        return authService.join(request);
    }

    @PostMapping("/login")
    public TokenResponseDto login(@RequestBody MemberLoginRequestDto request) {
        return authService.login(request);
    }

}
