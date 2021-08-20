package com.example.sbbank.service.member;

import com.example.sbbank.entity.member.MemberRepository;
import com.example.sbbank.payload.MemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public String join(MemberRequest request) {
        if(memberRepository.existsByNameOrUsername(request.getName(), request.getUsername())) {
            throw new RuntimeException();
        }

        request.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        memberRepository.save(request.toEntity());

        return "success join";
    }

}
