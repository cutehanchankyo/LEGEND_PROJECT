package com.example.workout.domain.email.presentation.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class EmailSendDto {

    @Email
    @NotBlank(message = "이메일은 필수입니다.")
    private final String email;

    @JsonCreator
    public EmailSendDto(@JsonProperty("email") String email){
        this.email = email;
    }
}
