package com.dev.mailservice.listener;

import com.dev.mailservice.model.EmailRequest;
import com.dev.mailservice.service.EmailService;
import com.dev.mailservice.service.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMQListener {

    private final EmailService emailService;
    private final ResponseService responseService;

    @RabbitListener(queues = "${spring.rabbitmq.queue.name}")
    public void handleEmailTask(EmailRequest emailRequest) {
        log.info("Received email task from RabbitMQ queue: {}", emailRequest.getId());
        emailService.handleEmailRequest(emailRequest);
        log.info("Email task processed successfully: {}", emailRequest.getId());
    }

} 