package com.swSoftware.asientos.seat_ms.domain.service.seat;

import com.swSoftware.asientos.seat_ms.application.dto.page.DtoPage;
import com.swSoftware.asientos.seat_ms.application.dto.seat.DtoSeat;
import com.swSoftware.asientos.seat_ms.application.usecase.GetAllSeatsUseCase;
import com.swSoftware.asientos.seat_ms.domain.model.SeatModel;
import com.swSoftware.asientos.seat_ms.infrastructure.adapter.mapper.SeatMapper;
import com.swSoftware.asientos.seat_ms.infrastructure.adapter.output.persistence.SeatRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GetAllSeatsService implements GetAllSeatsUseCase {

    private final SeatRepository seatRepository;
    private final SeatMapper seatMapper;

    @Override
    public DtoPage execute(UUID lastId, int limit) {
        Pageable limitProvider = PageRequest.of(0, limit);

        List<SeatModel> seats = seatRepository.findNextPage(lastId, limitProvider);
        List<DtoSeat> seatsDto = seats.stream().map(seatMapper::toDto).collect(Collectors.toList());
        String nextCursor = seats.isEmpty() ? null : seats.get(seats.size() - 1).getId().toString();

        return new DtoPage(nextCursor,seats.size() == limit,seatsDto);
    }
}
