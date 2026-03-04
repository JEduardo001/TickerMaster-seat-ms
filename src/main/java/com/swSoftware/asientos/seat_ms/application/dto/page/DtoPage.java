package com.swSoftware.asientos.seat_ms.application.dto.page;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
@Schema(description = "Generic wrapper for cursor-based paginated responses")
public record DtoPage<T>(
        @Schema(description = "Pointer to the next page of results (null if no more pages)", example = "MTc0MDU5MjAwMA==")
        String nextCursor,

        @Schema(description = "Indicates if there is a subsequent page available", example = "true")
        boolean hasNext,

        @Schema(description = "List of elements contained in the current page")
        List<T> elements
) {
}