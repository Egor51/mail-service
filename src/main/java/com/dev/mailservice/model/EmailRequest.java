package com.dev.mailservice.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {
    @NotNull(message = "ID cannot be null")
    private Long id;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Sender cannot be empty")
    @Email(message = "Invalid sender email format")
    private String sender;

    @NotBlank(message = "Recipient name cannot be empty")
    private String recipientName;

    private Subject subject;

    @NotBlank(message = "Content cannot be empty")
    private String content;

    @Override
    public String toString() {
        return String.format("EmailRequest(id=%d, email=%s, sender=%s, recipientName=%s)",
                id, email, sender, recipientName);
    }
} 