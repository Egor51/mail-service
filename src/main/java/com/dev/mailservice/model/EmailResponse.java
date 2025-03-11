package com.dev.mailservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailResponse {
    private Long requestId;
    private String email;
    private Status status;
}