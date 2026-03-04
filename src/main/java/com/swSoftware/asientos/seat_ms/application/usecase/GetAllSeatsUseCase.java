package com.swSoftware.asientos.seat_ms.application.usecase;

import com.swSoftware.asientos.seat_ms.application.dto.page.DtoPage;

import java.util.UUID;

public interface GetAllSeatsUseCase {
     DtoPage execute(UUID lastId, int limit);
}
