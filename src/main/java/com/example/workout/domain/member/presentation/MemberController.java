package com.example.workout.domain.member.presentation;

import com.example.workout.domain.member.presentation.dto.request.ChangePasswordRequest;
import com.example.workout.domain.member.presentation.dto.request.LoginRequest;
import com.example.workout.domain.member.presentation.dto.request.SignUpRequest;
import com.example.workout.domain.member.presentation.dto.response.MemberLoginResponse;
import com.example.workout.domain.member.presentation.dto.response.NewTokenResponse;
import com.example.workout.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> signUp(@RequestBody @Validated SignUpRequest signUpRequest){
        memberService.signUp(signUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<MemberLoginResponse> login(@RequestBody LoginRequest loginRequest){
        MemberLoginResponse data = memberService.login(loginRequest);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PatchMapping("/change-pw")
    public ResponseEntity<Void> changePassword(@RequestBody @Validated ChangePasswordRequest changePasswordRequest){
        memberService.changePassword(changePasswordRequest);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    public ResponseEntity<NewTokenResponse> reIssueToken(@RequestHeader("RefreshToken") String token){
        NewTokenResponse reIssueToken = memberService.tokenReissue(token);
        return new ResponseEntity<>(reIssueToken, HttpStatus.OK);
    }
}
