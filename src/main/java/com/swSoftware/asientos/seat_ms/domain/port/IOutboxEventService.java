package com.swSoftware.asientos.seat_ms.domain.port;

public interface IOutboxEventService<T> {
    void saveEvent(T request,String nameTopic);
}
