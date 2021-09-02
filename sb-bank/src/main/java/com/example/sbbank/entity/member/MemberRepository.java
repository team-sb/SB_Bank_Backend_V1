package com.example.sbbank.entity.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findByUsername(String username);
    boolean existsByNameOrUsername(String name, String username);
    boolean deleteByMemberId(Integer id);
}
