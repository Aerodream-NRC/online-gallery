package com.aerodream.user_service.Dto.Kafka;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventDto(
        UUID eventId,
        String eventType,
        LocalDateTime timestamp,
        Object object
) {
}