package com.example.smtpMailService.service.impl;

import com.example.smtpMailService.enums.StatusEnum;
import com.example.smtpMailService.model.SendEmailRequest;
import com.example.smtpMailService.model.StatusResponse;
import com.example.smtpMailService.service.StmpMailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
@Slf4j
@Component
@RequiredArgsConstructor
@EnableRabbit
public class StmpMailServiceImpl implements StmpMailService {

    private final RabbitTemplate rabbitTemplate;
    private final JavaMailSender javaMailSender;
    @Value("${queue.send-from-mail-service}")
    private String fromMailServiceQueue;

    /**
     * Method to send message to users emails and send response to Core Service.
     *
     * @param request SendEmailRequest.class
     */
    @Override
    public void sendEmail(SendEmailRequest request) {
        StatusResponse statusResponse = StatusResponse.builder()
                .uniqueMessage(request.getUniqueMessage())
                .status(StatusEnum.SUCCESS.getStatus()).build();
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(request.getEmails().toArray(new String[0]));
            simpleMailMessage.setSubject("Default"); // TODO: 16.01.2024
            simpleMailMessage.setText(request.getMessage());
            javaMailSender.send(simpleMailMessage);
            rabbitTemplate.convertAndSend(fromMailServiceQueue, statusResponse);
        } catch (Exception e) {
            log.info(e.getMessage());
            statusResponse.setStatus(StatusEnum.FAILED.getStatus());
            rabbitTemplate.convertAndSend(fromMailServiceQueue, statusResponse);
        }
    }
}
