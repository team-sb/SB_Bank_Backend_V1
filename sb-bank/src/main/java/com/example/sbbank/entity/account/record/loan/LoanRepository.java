package com.example.sbbank.entity.account.record.loan;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<LoanRecord, Integer> {
    List<LoanRecord> findByMemberId(Integer id);
    void deleteAllByMemberId(Integer id);
}
