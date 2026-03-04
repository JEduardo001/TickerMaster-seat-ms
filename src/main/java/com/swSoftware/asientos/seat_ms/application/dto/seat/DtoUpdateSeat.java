package com.swSoftware.asientos.seat_ms.application.dto.seat;

import com.swSoftware.asientos.seat_ms.domain.status.StatusSeat;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record DtoUpdateSeat(
        @NotNull
        UUID id,
        @NotNull
        StatusSeat status
) {
}
