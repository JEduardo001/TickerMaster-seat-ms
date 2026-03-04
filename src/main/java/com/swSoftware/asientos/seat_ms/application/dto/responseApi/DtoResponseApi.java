package com.swSoftware.asientos.seat_ms.application.dto.responseApi;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Standard generic API response wrapper")
public record DtoResponseApi<T>(
        @Schema(description = "Success or informational message", example = "Operation completed successfully")
        String message,

        @Schema(description = "HTTP status code", example = "200")
        Integer status,

        @Schema(description = "Correlation ID for request tracing", example = "550e8400-e29b-41d4-a716-446655440000")
        String idCorrelation,

        @Schema(description = "Generic data payload containing the requested resource")
        T data
) {
}
