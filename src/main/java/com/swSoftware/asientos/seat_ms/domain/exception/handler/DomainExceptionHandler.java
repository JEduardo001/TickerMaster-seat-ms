package com.swSoftware.asientos.seat_ms.domain.exception.handler;

import com.swSoftware.asientos.seat_ms.application.dto.responseApi.DtoErrorResponseApi;
import com.swSoftware.asientos.seat_ms.domain.exception.ExceptionSeatNotFound;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

import static com.swSoftware.asientos.seat_ms.domain.common.HeaderConstants.CORRELATION_KEY;

public abstract class DomainExceptionHandler {

    private String getIdCorrelation(){
        return MDC.get(CORRELATION_KEY.toString());
    }

    @ExceptionHandler(ExceptionSeatNotFound.class)
    public ResponseEntity<DtoErrorResponseApi> ExceptionSeatNotFound(ExceptionSeatNotFound ex) {
        return ResponseEntity.status(422).body(new DtoErrorResponseApi("SEAT_NOT_FOUND", 404, getIdCorrelation()));
    }

}

