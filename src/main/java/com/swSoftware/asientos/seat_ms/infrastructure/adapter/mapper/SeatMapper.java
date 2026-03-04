package com.swSoftware.asientos.seat_ms.infrastructure.adapter.mapper;


import com.swSoftware.asientos.seat_ms.application.dto.seat.DtoSeat;
import com.swSoftware.asientos.seat_ms.domain.model.SeatModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SeatMapper {
    DtoSeat toDto(SeatModel request);
}
