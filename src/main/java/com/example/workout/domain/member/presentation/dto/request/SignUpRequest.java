package com.example.workout.domain.member.presentation.dto.request;

import com.example.workout.domain.member.entity.Member;
import com.example.workout.global.role.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp="(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 특수문자 1개 이상 글자 수는 8 ~ 16자 사이여야 합니다.")
    private String password;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @NotBlank(message = "학번는 필수 입력 값입니다")
    private String number;

    @NotBlank(message = "역할을 지정해주세요.")
    private String role;

    public Member toEntity(){
        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .number(number)
                .role(Role.from(role))
                .build();
    }
}
