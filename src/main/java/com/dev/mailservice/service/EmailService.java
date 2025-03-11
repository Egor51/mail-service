package com.dev.mailservice.service;

import com.dev.mailservice.model.EmailRequest;
import com.dev.mailservice.model.EmailResponse;
import com.dev.mailservice.model.Status;
import com.dev.mailservice.util.EmailTemplateUtil;
import jakarta.annotation.PreDestroy;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final ResponseService responseService;
    private final ExecutorService executorService = Executors.newFixedThreadPool(50);

    public void handleEmailRequest(EmailRequest request) {
        log.info("Received email request: {}", request.getId());
        responseService.sendResponse(createResponse(request, Status.ACCEPTED));
        CompletableFuture.runAsync(() -> {
            try {
                responseService.sendResponse(createResponse(request, Status.PROCESSING));
                sendEmail(request);
                responseService.sendResponse(createResponse(request, Status.SENT));
                log.info("Successfully sent email for request: {}", request.getId());
            } catch (Exception e) {
                log.error("Failed to send email for request: {}", request.getId(), e);
                responseService.sendResponse(createResponse(request, Status.FAILED));
            }
        }, executorService);
    }

    private EmailResponse createResponse(EmailRequest request, Status status) {
        return EmailResponse.builder()
                .requestId(request.getId())
                .email(request.getEmail())
                .status(status)
                .build();
    }

    private void sendEmail(EmailRequest request) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(request.getSender());
        helper.setTo(request.getEmail());
        
        configureEmailBySubject(helper, request);
        mailSender.send(message);
    }

    /**
     * Конфигурирует письмо в зависимости от типа Subject
     */
    private void configureEmailBySubject(MimeMessageHelper helper, EmailRequest request) throws MessagingException {
        switch (request.getSubject()) {
            case REGISTRATION -> {
                helper.setSubject("Добро пожаловать!");
                String content = EmailTemplateUtil.getRegistrationTemplate(
                    request.getRecipientName(),
                    request.getContent() // Здесь должны быть данные регистрации
                );
                helper.setText(content, true);
            }
            case PASSWORD_RESET -> {
                helper.setSubject("Восстановление пароля");
                String content = EmailTemplateUtil.getPasswordResetTemplate(
                    request.getRecipientName(),
                    request.getContent() // Здесь должна быть ссылка или новый пароль
                );
                helper.setText(content, true);
            }
            case ADVERTISING -> {
                helper.setSubject("Специальное предложение");
                String content = EmailTemplateUtil.getAdvertisingTemplate(
                    request.getRecipientName()
                );
                helper.setText(content, true);
            }
            case TEXT_MESSAGE -> {
                helper.setSubject("Новое сообщение");
                // Для текстового сообщения используем контент как есть
                helper.setText(request.getContent(), false);
            }
        }
    }

    @PreDestroy
    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}