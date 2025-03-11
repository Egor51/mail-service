package com.dev.mailservice.model;

public enum Status {
    ACCEPTED,    // Запрос принят в обработку
    PROCESSING,  // Email в процессе отправки
    SENT,        // Email успешно отправлен
    FAILED       // Ошибка при отправке
} 