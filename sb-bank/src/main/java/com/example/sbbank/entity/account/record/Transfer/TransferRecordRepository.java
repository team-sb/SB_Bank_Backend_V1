package com.example.sbbank.entity.account.record.Transfer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransferRecordRepository extends JpaRepository<TransferRecord, Long> {
    List<TransferRecord> findByMemberId(Integer id);
}