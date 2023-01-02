package com.example.workout.domain.member.service.Impl;

import com.example.workout.domain.email.entity.EmailAuth;
import com.example.workout.domain.email.exception.NotVerifyEmailException;
import com.example.workout.domain.email.repository.EmailAuthRepository;
import com.example.workout.domain.member.exception.MisMatchPasswordException;
import com.example.workout.domain.member.exception.RefreshTokenNotFoundException;
import com.example.workout.domain.member.presentation.dto.request.ChangePasswordRequest;
import com.example.workout.domain.member.presentation.dto.request.LoginRequest;
import com.example.workout.domain.member.presentation.dto.request.SignUpRequest;
import com.example.workout.domain.member.presentation.dto.response.MemberLoginResponse;
import com.example.workout.domain.member.presentation.dto.response.NewTokenResponse;
import com.example.workout.domain.member.entity.Member;
import com.example.workout.domain.member.entity.RefreshToken;
import com.example.workout.domain.member.exception.ExistEmailException;
import com.example.workout.domain.member.exception.MemberNotFoundException;
import com.example.workout.domain.member.repository.MemberRepository;
import com.example.workout.domain.member.repository.RefreshTokenRepository;
import com.example.workout.domain.member.service.MemberService;
import com.example.workout.global.exception.collection.TokenNotValidException;
import com.example.workout.global.role.Role;
import com.example.workout.global.security.jwt.TokenProvider;
import com.example.workout.global.security.jwt.properties.JwtProperties;
import com.example.workout.global.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final JwtProperties jwtProperties;
    private final EmailAuthRepository emailAuthRepository;
    private final MemberUtil memberUtil;

    @Transactional
    public MemberLoginResponse login(LoginRequest loginRequest) {
        Member member = memberRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다"));
        if(!passwordEncoder.matches(loginRequest.getPassword(), member.getPassword())){
            throw new MisMatchPasswordException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = tokenProvider.generatedAccessToken(loginRequest.getEmail());
        String refreshToken = tokenProvider.generatedRefreshToken(loginRequest.getEmail());
        RefreshToken entityToRedis = new RefreshToken(loginRequest.getEmail(), refreshToken, tokenProvider.getREFRESH_TOKEN_EXPIRE_TIME());
        refreshTokenRepository.save(entityToRedis);

        return MemberLoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiredAt(tokenProvider.getExpiredAtToken(accessToken, jwtProperties.getAccessSecret()))
                .build();
    }


    @Transactional(rollbackFor = Exception.class)
    public void signUp(SignUpRequest signUpRequest) {
        boolean isExist = memberRepository.existsByEmail(signUpRequest.getEmail());
        if(isExist){
            throw new ExistEmailException("이미 존재하는 이메일입니다.");
        }
        EmailAuth emailAuth = emailAuthRepository.findById(signUpRequest.getEmail())
                .orElseThrow(() -> new NotVerifyEmailException("인증되지 않은 이메일입니다."));

        if(!emailAuth.getAuthentication()){
            throw new NotVerifyEmailException("인증되지 않은 이메일입니다.");
        }

        Member member = Member.builder()
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .name(signUpRequest.getName())
                .number(signUpRequest.getNumber())
                .role(Role.from(signUpRequest.getRole()))
                .build();
        memberRepository.save(member);
    }

    @Transactional(rollbackFor = Exception.class)
    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        Member member = memberUtil.currentMember();
        validateAuth(member.getEmail());
        member.updatePassword(passwordEncoder.encode(changePasswordRequest.getPassword()));
        memberRepository.save(member);
    }

    @Transactional(rollbackFor = Exception.class)
    public void validateAuth(String email) {
        EmailAuth emailAuth = emailAuthRepository.findById(email)
                .orElseThrow(() -> new NotVerifyEmailException("검증되지 않은 이메일입니다."));
        if(!emailAuth.getAuthentication()){
            throw new NotVerifyEmailException("검증되지 않은 이메일입니다");
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public NewTokenResponse tokenReissue(String requestToken) {
        String email = tokenProvider.getUserEmail(requestToken, jwtProperties.getRefreshSecret());
        RefreshToken token = refreshTokenRepository.findById(email)
                .orElseThrow(() -> new RefreshTokenNotFoundException("리프레시 토큰이 존재하지 없습니다."));

        if(!token.getToken().equals(requestToken)) {
            throw new TokenNotValidException("검증되지 않은 토큰입니다.");
        }

        String accessToken = tokenProvider.generatedAccessToken(email);
        String refreshToken = tokenProvider.generatedRefreshToken(email);
        ZonedDateTime expiredAt = tokenProvider.getExpiredAtToken(accessToken, jwtProperties.getAccessSecret());
        token.exchangeRefreshToken(refreshToken);
        refreshTokenRepository.save(token);

        return NewTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiredAt(expiredAt)
                .build();
    }

}

