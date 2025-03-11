package com.dev.mailservice.service;

import com.dev.mailservice.model.EmailResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResponseService {

    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.exchange.name}")
    private String exchange;

    @Value("${spring.rabbitmq.response.routing.key}")
    private String responseRoutingKey;

    public void sendResponse(EmailResponse response) {
        log.debug("Sending response for email task: {}", response.getRequestId());
        rabbitTemplate.convertAndSend(exchange, responseRoutingKey, response);
    }
} 