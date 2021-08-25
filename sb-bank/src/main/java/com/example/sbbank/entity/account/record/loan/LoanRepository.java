package com.example.sbbank.entity.account.record.loan;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<LoanRecord, Integer> {
}
