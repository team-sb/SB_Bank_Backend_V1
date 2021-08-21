package com.example.sbbank.entity.account;

import com.example.sbbank.entity.member.Member;
import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private Integer accountNumber;

    @Setter
    private Integer balance;

    @OneToOne
    @JoinColumn(name = "memberId")
    private Member member;

}
