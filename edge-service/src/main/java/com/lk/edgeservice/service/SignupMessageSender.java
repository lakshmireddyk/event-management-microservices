package com.lk.edgeservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lk.edgeservice.model.Account;
import com.lk.edgeservice.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignupMessageSender {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public SignupMessageSender(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendSignupMessage(Account account) {
        this.rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_SIGNUP, account);
    }
}
