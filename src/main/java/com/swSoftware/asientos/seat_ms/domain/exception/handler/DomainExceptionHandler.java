package com.swSoftware.asientos.seat_ms.domain.exception.handler;

import org.slf4j.MDC;

import static com.swSoftware.asientos.seat_ms.domain.common.HeaderConstants.CORRELATION_KEY;

public abstract class DomainExceptionHandler {

    private String getIdCorrelation(){
        return MDC.get(CORRELATION_KEY.toString());
    }




}

