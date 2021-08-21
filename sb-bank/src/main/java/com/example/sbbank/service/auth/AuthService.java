package com.example.sbbank.service.auth;

import com.example.sbbank.entity.member.Member;
import com.example.sbbank.entity.member.MemberRepository;
import com.example.sbbank.exception.InvalidPasswordException;
import com.example.sbbank.exception.UserAlreadyExistsException;
import com.example.sbbank.exception.UserNotFoundException;
import com.example.sbbank.security.jwt.JwtTokenProvider;
import com.example.sbbank.payload.request.MemberLoginRequestDto;
import com.example.sbbank.payload.request.MemberJoinRequestDto;
import com.example.sbbank.payload.response.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public String join(MemberJoinRequestDto request) {
        if(memberRepository.existsByNameOrUsername(request.getName(), request.getUsername())) {
            throw new UserAlreadyExistsException();
        }

        request.setPassword(passwordEncoder.encode(request.getPassword()));
        memberRepository.save(request.toEntity());

        return "success join";
    }

    public TokenResponseDto login(MemberLoginRequestDto request) {
        Member member = memberRepository.findByUsername(request.getUsername())
                .orElseThrow(UserNotFoundException::new);

        if(!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new InvalidPasswordException();
        }

        return tokenProvider.createToken(member.getUsername(), member.getAuthority());
    }
}
