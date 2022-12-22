package com.example.workout.domain.email.presentation.controller;

import com.example.workout.domain.email.presentation.request.EmailSendDto;
import com.example.workout.domain.email.service.EmailSendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {

    private final EmailSendService emailSendService;

    @PostMapping(value = "/send", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> sendEmail(@RequestBody @Valid EmailSendDto emailSendDto){
        emailSendService.sendEmail(emailSendDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
