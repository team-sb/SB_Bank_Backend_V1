package com.example.sbbank.entity.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByMemberId(Integer id);
    Optional<Account> findByAccountNumber(Integer target);
    boolean existsByMemberId(Integer id);
    boolean deleteByMemberId(Integer id);
}
