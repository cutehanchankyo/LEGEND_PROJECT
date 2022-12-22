package com.example.workout.domain.member.service;

import com.example.workout.domain.member.presentation.dto.request.ChangePasswordRequest;
import com.example.workout.domain.member.presentation.dto.request.LoginRequest;
import com.example.workout.domain.member.presentation.dto.request.SignUpRequest;
import com.example.workout.domain.member.presentation.dto.response.MemberLoginResponse;
import com.example.workout.domain.member.presentation.dto.response.NewTokenResponse;

public interface MemberService {

    public MemberLoginResponse login(LoginRequest loginRequest);
    public void signUp(SignUpRequest signUpRequest);
    public void changePassword(ChangePasswordRequest changePasswordRequest);
    public void validateAuth(String email);
    public NewTokenResponse tokenReissue(String requestToken);
}
