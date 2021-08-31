package com.example.sbbank.service.admin;

import com.example.sbbank.entity.account.AccountRepository;
import com.example.sbbank.entity.member.MemberRepository;
import com.example.sbbank.exception.AccountNotFoundException;
import com.example.sbbank.payload.request.UpdateUserBalanceRequestDto;
import com.example.sbbank.payload.response.ShowUserAccountInfoResponseDto;
import com.example.sbbank.payload.response.ShowUserListResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final MemberRepository memberRepository;
    private final AccountRepository accountRepository;

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

    @Override
    public ShowUserAccountInfoResponseDto showUserAccountInfo(Integer id) {

        return accountRepository.findByMemberId(id)
                .map(account -> new ShowUserAccountInfoResponseDto(
                        account.getMember().getName(),
                        account.getAccountNumber(),
                        account.getBalance()
                ))
                .orElseThrow(AccountNotFoundException::new);
    }

    @Override
    public String updateUserBalance(UpdateUserBalanceRequestDto request, Integer id) {

        accountRepository.findByMemberId(id)
                .map(account -> {
                    account.setBalance(request.getMoney());
                    accountRepository.save(account);
                    return account;
                })
                .orElseThrow(AccountNotFoundException::new);

        return "success update";
    }

}
