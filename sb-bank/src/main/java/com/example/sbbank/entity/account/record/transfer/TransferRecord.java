package com.example.sbbank.entity.account.record.Transfer;

import com.example.sbbank.entity.member.Member;
import com.example.sbbank.entity.Transaction;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "transfer")
public class TransferRecord {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer target;

    @Setter
    private Integer money;

    @Setter
    @Enumerated(EnumType.STRING)
    private Transaction transactionType;
    private Date transactionDate;
    private Integer bfBalance;
    private Integer aftBalance;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;
}
