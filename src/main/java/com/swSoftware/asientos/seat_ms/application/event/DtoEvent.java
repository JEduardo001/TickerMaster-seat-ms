package com.swSoftware.asientos.seat_ms.application.event;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.Instant;

@Builder
@Schema(description = "Generic wrapper for inter-service event communication")
public record DtoEvent<T>(
        @Schema(description = "Unique correlation identifier to track the event across the system", example = "550e8400-e29b-41d4-a716-446655440000")
        String correlationId,

        @Schema(description = "Timestamp when the event was generated", example = "2026-03-03T14:26:00Z")
        Instant createdAt,

        @Schema(description = "Generic event payload containing the business data")
        T data
) {
}
