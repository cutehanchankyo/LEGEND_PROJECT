package com.example.workout.domain.member.exception;

import com.example.workout.global.exception.ErrorCode;

public class MisMatchPasswordException extends RuntimeException{
    private final ErrorCode errorCode;

    public MisMatchPasswordException(String message){
        super(message);
        this.errorCode = ErrorCode.MISMATCH_PASSWORD;
    }
}
