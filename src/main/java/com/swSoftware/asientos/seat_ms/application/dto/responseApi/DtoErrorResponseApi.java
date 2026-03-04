package com.swSoftware.asientos.seat_ms.application.dto.responseApi;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Standard API error response structure")
public record DtoErrorResponseApi(
        @Schema(description = "Detailed error message", example = "User not found with the provided ID")
        String message,

        @Schema(description = "HTTP status code", example = "404")
        Integer status,

        @Schema(description = "Correlation ID to track the request in logs and across microservices", example = "550e8400-e29b-41d4-a716-446655440000")
        String idCorrelation
) {
}
