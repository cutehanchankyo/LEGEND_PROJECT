package com.example.workout.domain.member.repository;

import com.example.workout.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByName(String name);
    List<Member> findAll();
    boolean existsByEmail(String email);
}
