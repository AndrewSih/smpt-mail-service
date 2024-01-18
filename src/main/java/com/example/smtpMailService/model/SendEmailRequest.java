package com.example.smtpMailService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendEmailRequest implements Serializable {

    private String message;
    private List<String> emails;
    private String uniqueMessage;
}
