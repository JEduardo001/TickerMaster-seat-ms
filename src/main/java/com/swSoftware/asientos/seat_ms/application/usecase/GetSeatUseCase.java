package com.swSoftware.asientos.seat_ms.application.usecase;

import com.swSoftware.asientos.seat_ms.application.dto.seat.DtoSeat;

import java.util.UUID;

public interface GetSeatUseCase {
    DtoSeat execute(UUID idSeat);

}
