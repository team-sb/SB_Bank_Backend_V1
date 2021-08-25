package com.example.sbbank.controller;

import com.example.sbbank.payload.request.MemberLoginRequestDto;
import com.example.sbbank.payload.request.MemberJoinRequestDto;
import com.example.sbbank.payload.request.MemberSecLoginRequestDto;
import com.example.sbbank.payload.response.AccessTokenResponseDto;
import com.example.sbbank.payload.response.SecTokenResponseDto;
import com.example.sbbank.security.auth.CustomUserDetails;
import com.example.sbbank.service.auth.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthServiceImpl authServiceImpl;

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.CREATED)
    public String join(@Valid @RequestBody MemberJoinRequestDto request) {
        return authServiceImpl.join(request);
    }

    @PostMapping("/login")
    public AccessTokenResponseDto login(@RequestBody MemberLoginRequestDto request) {
        return authServiceImpl.login(request);
    }

    @PostMapping("/sec-login")
    public SecTokenResponseDto secLogin(@RequestBody MemberSecLoginRequestDto request,
                                        @AuthenticationPrincipal CustomUserDetails userDetails) {
        return authServiceImpl.secLogin(request, userDetails.getMember());
    }

}
