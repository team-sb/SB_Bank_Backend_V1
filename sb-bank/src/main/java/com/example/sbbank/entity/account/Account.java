package com.example.sbbank.entity.account;

import com.example.sbbank.entity.member.Member;
import com.example.sbbank.service.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Transaction transactionType;
    private Date transactionDate;
    private Long balance; // 잔액

    @OneToOne
    @JoinColumn(name = "memberId")
    private Member member;
}
