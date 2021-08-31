package com.example.sbbank.service.auth;

import com.example.sbbank.entity.Authority;
import com.example.sbbank.entity.member.Member;
import com.example.sbbank.entity.member.MemberRepository;
import com.example.sbbank.exception.InvalidPasswordException;
import com.example.sbbank.exception.UserAlreadyExistsException;
import com.example.sbbank.exception.UserNotFoundException;
import com.example.sbbank.payload.request.MemberSecLoginRequestDto;
import com.example.sbbank.payload.response.SecTokenResponseDto;
import com.example.sbbank.security.jwt.JwtTokenProvider;
import com.example.sbbank.payload.request.MemberLoginRequestDto;
import com.example.sbbank.payload.request.MemberJoinRequestDto;
import com.example.sbbank.payload.response.AccessTokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    @Override
    public String join(MemberJoinRequestDto request) {
        if(memberRepository.existsByNameOrUsername(request.getName(), request.getUsername())) {
            throw new UserAlreadyExistsException();
        }

        memberRepository.save(
                Member.builder()
                        .name(request.getName())
                        .username(request.getUsername())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .secPassword(passwordEncoder.encode(request.getSecPassword()))
                        .authority(Authority.ROLE_ADMIN)
                        .build());

        return "success join";
    }

    @Override
    public AccessTokenResponseDto login(MemberLoginRequestDto request) {
        Member member = memberRepository.findByUsername(request.getUsername())
                .orElseThrow(UserNotFoundException::new);

        if(!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new InvalidPasswordException();
        }

        return tokenProvider.createAccessToken(member.getUsername(), member.getAuthority());
    }

    @Override
    public SecTokenResponseDto secLogin(MemberSecLoginRequestDto request, Member member) {
        if(!passwordEncoder.matches(request.getSecPassword(), member.getSecPassword())) {
            throw new InvalidPasswordException();
        }

        Member memberAuthority = setAuthority(member);

        return tokenProvider.createSecToken(member.getUsername(), memberAuthority.getAuthority());
    }

    public Member setAuthority(Member member) {
        return memberRepository.findById(member.getId())
                .map(member1 -> {
                    member1.setAuthority(Authority.ROLE_MANAGER);
                    memberRepository.save(member1);
                    return member1;
                })
                .orElseThrow(UserNotFoundException::new);
    }

}
