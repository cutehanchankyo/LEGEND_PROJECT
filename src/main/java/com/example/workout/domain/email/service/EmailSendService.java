package com.example.workout.domain.email.service;

import com.example.workout.domain.email.entity.EmailAuth;
import com.example.workout.domain.email.presentation.request.EmailSendDto;

public interface EmailSendService {
    public void sendEmail(EmailSendDto emailSendDto);
    public void checkEmail(String email, String authKey);
}
