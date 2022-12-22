package com.example.workout.domain.member.exception;

import com.example.workout.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class ExistEmailException extends RuntimeException{

    private final ErrorCode errorCode;

    public ExistEmailException(String message){
        super(message);
        this.errorCode = ErrorCode.ALREADY_EXIST_EMAIL;
    }
}
