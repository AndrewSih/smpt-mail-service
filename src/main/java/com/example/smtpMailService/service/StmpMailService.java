package com.example.smtpMailService.service;

import com.example.smtpMailService.model.SendEmailRequest;
import org.springframework.stereotype.Service;

@Service
public interface StmpMailService {

    void sendEmail(SendEmailRequest request);
}
