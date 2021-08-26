package com.example.sbbank.service.admin;

import com.example.sbbank.entity.member.MemberRepository;
import com.example.sbbank.payload.response.ShowUserListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final MemberRepository memberRepository;

    @Override
    public ShowUserListResponseDto showUsers() {
        List<ShowUserListResponseDto.users> userList = memberRepository.findAll()
                .stream()
                .map(member -> new ShowUserListResponseDto.users(
                        member.getId(),
                        member.getName(),
                        member.getUsername(),
                        member.getAuthority(),
                        member.getAccount().getAccountNumber()
                ))
                .collect(Collectors.toList());

        return new ShowUserListResponseDto(userList);
    }

}
