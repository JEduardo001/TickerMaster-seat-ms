package com.swSoftware.asientos.seat_ms.domain.port;

import com.app.events.ReserveEvent;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface ISeatService {
    void reserveSeat(ReserveEvent request) throws JsonProcessingException;
}
