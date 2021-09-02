package com.example.sbbank.service.auth;


import com.example.sbbank.entity.member.Member;
import com.example.sbbank.payload.request.MemberJoinRequestDto;
import com.example.sbbank.payload.request.MemberLoginRequestDto;
import com.example.sbbank.payload.request.MemberSecLoginRequestDto;
import com.example.sbbank.payload.response.AccessTokenResponseDto;
import com.example.sbbank.payload.response.SecTokenResponseDto;
import com.example.sbbank.payload.response.UserExitResponseDto;

public interface AuthService {
    String join(MemberJoinRequestDto request);
    AccessTokenResponseDto login(MemberLoginRequestDto request);
    UserExitResponseDto exit(Integer id);
    SecTokenResponseDto secLogin(MemberSecLoginRequestDto request, Member member);
}
