package com.example.sbbank.entity.account;

import com.example.sbbank.entity.member.Member;
import com.example.sbbank.entity.Transaction;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Record {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer target;

    @Setter
    private Integer money; // 거래 금액

    @Setter
    @Enumerated(EnumType.STRING)
    private Transaction transactionType;
    private Date transactionDate;
    private Integer bfBalance; // 잔액
    private Integer aftBalance;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member; // 계좌 주인
}
