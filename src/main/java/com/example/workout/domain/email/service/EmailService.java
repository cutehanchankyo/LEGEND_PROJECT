package com.example.workout.domain.email.service;

import com.example.workout.domain.email.presentation.request.EmailSendDto;

public interface EmailService {
    public void sendEmail(EmailSendDto emailSendDto);
    public void checkEmail(String email, String verifyCode);
}
