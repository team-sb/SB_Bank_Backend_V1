package com.example.sbbank.controller.member;

import com.example.sbbank.payload.MemberRequest;
import com.example.sbbank.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/auth/join")
    public String join(@RequestBody MemberRequest request) {
        memberService.join(request);
        return "success join";
    }

}
