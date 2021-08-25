package com.example.sbbank.entity.member;

import com.example.sbbank.entity.Authority;
import com.example.sbbank.entity.account.Account;
import lombok.*;
import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String username;
    private String password;
    private String secPassword;

    @Setter
    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Setter
    @OneToOne(mappedBy = "member")
    private Account account;

}
