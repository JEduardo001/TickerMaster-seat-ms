package com.swSoftware.asientos.seat_ms.domain.service.seat;


import com.swSoftware.asientos.seat_ms.application.dto.seat.DtoSeat;
import com.swSoftware.asientos.seat_ms.application.dto.seat.DtoUpdateSeat;
import com.swSoftware.asientos.seat_ms.application.usecase.UpdateSeatUseCase;
import com.swSoftware.asientos.seat_ms.domain.exception.ExceptionSeatNotFound;
import com.swSoftware.asientos.seat_ms.domain.model.SeatModel;
import com.swSoftware.asientos.seat_ms.infrastructure.adapter.mapper.SeatMapper;
import com.swSoftware.asientos.seat_ms.infrastructure.adapter.output.persistence.SeatRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateSeatService implements UpdateSeatUseCase {

    private final SeatRepository seatRepository;
    private final SeatMapper seatMapper;

    @Override
    public DtoSeat execute(DtoUpdateSeat request){
        SeatModel seat = seatRepository.findById(request.id()).orElseThrow(ExceptionSeatNotFound::new);
        seat.setStatus(request.status());
        return seatMapper.toDto(seatRepository.save(seat));
    }
}
