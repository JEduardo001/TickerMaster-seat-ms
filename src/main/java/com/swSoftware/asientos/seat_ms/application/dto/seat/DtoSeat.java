package com.swSoftware.asientos.seat_ms.application.dto.seat;

import com.swSoftware.asientos.seat_ms.domain.status.SectionSeat;
import com.swSoftware.asientos.seat_ms.domain.status.StatusSeat;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record DtoSeat(
        UUID id,
        Integer row,
        SectionSeat section,
        Integer seatNumber,
        StatusSeat status,
        Instant createAt
) {
}