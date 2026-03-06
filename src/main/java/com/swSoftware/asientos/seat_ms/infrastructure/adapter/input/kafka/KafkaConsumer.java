package com.swSoftware.asientos.seat_ms.infrastructure.adapter.input.kafka;

import com.swSoftware.asientos.seat_ms.domain.port.ISeatService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import com.app.events.ReserveEvent;


@Service
@AllArgsConstructor
@Slf4j
public class KafkaConsumer {

    private final ISeatService iSeatService;

    @KafkaListener(
            topics = "dev.user-ms.reserve-seat.v2",
            groupId = "user-ms.reserve-seat.v2"    )
    public void responseVerifyUserToReserveSeat(ReserveEvent request, @Header(value = "CORRELATION_HEADER",required = false) String correlationId)
        throws Exception{
        iSeatService.reserveSeat(request);
    }
}

