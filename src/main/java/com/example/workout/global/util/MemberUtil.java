package com.example.workout.global.util;

import com.example.workout.domain.member.entity.Member;
import com.example.workout.domain.member.exception.MemberNotFoundException;
import com.example.workout.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MemberUtil {

    private final MemberRepository memberRepository;

    public Member currentMember(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다"));
    }
}
