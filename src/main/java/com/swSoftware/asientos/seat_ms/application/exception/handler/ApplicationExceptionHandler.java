package com.swSoftware.asientos.seat_ms.application.exception.handler;

import com.swSoftware.asientos.seat_ms.domain.exception.handler.DomainExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import static com.swSoftware.asientos.seat_ms.domain.common.HeaderConstants.CORRELATION_KEY;

@Slf4j
public abstract class ApplicationExceptionHandler extends DomainExceptionHandler {

    private String getIdCorrelation(){
        return MDC.get(CORRELATION_KEY.toString());
    }


}

