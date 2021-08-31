package com.example.sbbank.entity.account.record.loan;

import com.example.sbbank.entity.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "loan")
public class LoanRecord {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Max(value = 10000000)
    private Integer money;
    private Double interest;
    private Date borrowedDate;
    private Date loanExpirationDate;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

}
