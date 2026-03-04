package com.swSoftware.asientos.seat_ms.application.usecase;

import com.swSoftware.asientos.seat_ms.application.dto.seat.DtoSeat;
import com.swSoftware.asientos.seat_ms.application.dto.seat.DtoUpdateSeat;


public interface UpdateSeatUseCase {
    DtoSeat execute(DtoUpdateSeat request);
}
