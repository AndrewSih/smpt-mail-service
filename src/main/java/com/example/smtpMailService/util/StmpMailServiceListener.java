package com.example.smtpMailService.util;

import com.example.smtpMailService.model.SendEmailRequest;
import com.example.smtpMailService.service.StmpMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@EnableRabbit
public class StmpMailServiceListener {

    private final StmpMailService service;

    /**
     * Method to get message from core service and send to users email.
     *
     * @param request SendEmailRequest.class
     */
    @RabbitListener(queues = "${queue.send-to-mail-service}")
    public void saveAndSendMessage(SendEmailRequest request) {
        System.out.println(request);
        service.sendEmail(request);
    }
}
