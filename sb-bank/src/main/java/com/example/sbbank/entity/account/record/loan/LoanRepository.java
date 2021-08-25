package com.example.sbbank.entity.account.record.loan;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoanRepository extends JpaRepository<LoanRecord, Integer> {
    Optional<LoanRecord> findByMemberId(Integer id);
}
