package com.swSoftware.asientos.seat_ms.domain.service.seat;

import com.swSoftware.asientos.seat_ms.application.dto.seat.DtoSeat;
import com.swSoftware.asientos.seat_ms.application.usecase.GetSeatUseCase;
import com.swSoftware.asientos.seat_ms.domain.exception.ExceptionSeatNotFound;
import com.swSoftware.asientos.seat_ms.infrastructure.adapter.mapper.SeatMapper;
import com.swSoftware.asientos.seat_ms.infrastructure.adapter.output.persistence.SeatRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class GetSeatService implements GetSeatUseCase {

    private final SeatRepository seatRepository;
    private final SeatMapper seatMapper;

    @Override
    public DtoSeat execute(UUID id){
        return seatMapper.toDto(seatRepository.findById(id).orElseThrow(ExceptionSeatNotFound::new));
    }
}
