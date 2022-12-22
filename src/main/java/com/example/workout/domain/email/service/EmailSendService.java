package com.example.workout.domain.email.service;

import com.example.workout.domain.email.presentation.request.EmailSendDto;

public interface EmailSendService {
    void sendEmail(EmailSendDto emailSendDto);
}
