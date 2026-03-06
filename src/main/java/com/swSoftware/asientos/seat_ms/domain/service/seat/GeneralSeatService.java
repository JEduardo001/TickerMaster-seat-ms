package com.swSoftware.asientos.seat_ms.domain.service.seat;

import com.app.events.ReserveEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.swSoftware.asientos.seat_ms.domain.model.EventProcessedModel;
import com.swSoftware.asientos.seat_ms.domain.model.SeatModel;
import com.swSoftware.asientos.seat_ms.domain.port.IEventProcessedService;
import com.swSoftware.asientos.seat_ms.domain.port.IOutboxEventService;
import com.swSoftware.asientos.seat_ms.domain.port.ISeatService;
import com.swSoftware.asientos.seat_ms.domain.status.StatusSeat;
import com.swSoftware.asientos.seat_ms.infrastructure.adapter.output.persistence.SeatRepository;
import jakarta.persistence.LockModeType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static com.swSoftware.asientos.seat_ms.infrastructure.shared.LogMessages.*;

@Service
@AllArgsConstructor
@Slf4j
public class GeneralSeatService implements ISeatService {

    private final SeatRepository seatRepository;
    private final IEventProcessedService iEventProcessedService;
    private final IOutboxEventService iOutboxEventService;
    private final String topicReservedSeats = "dev.seat-ms.reserved-seats.v1";

    private void saveOutboxEvent(ReserveEvent request, String topic){
        iOutboxEventService.saveEvent(request,topic);

    }

    private void saveEventProcessed(ReserveEvent request){
       iEventProcessedService.saveEventProcessed(EventProcessedModel.builder()
               .id(request.getIdCorrelation())
               .createdAt(Instant.now())
               .data(request.toString())
               .build());
    }


    @Override
    @Transactional()
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public void reserveSeat(ReserveEvent request) {

        if(iEventProcessedService.eventAlreadyProcessed(request.getIdCorrelation())){
            log.warn(MESSAGE_EVENT_ALREADY_PROCESSED.toString());
            return;
        }

        List<SeatModel> seatToReserve = seatRepository.findAllById(request.getIdsSeat());

        if(seatToReserve.size() < request.getIdsSeat().size()){
            log.warn(MESSAGE_SEAT_NOT_FOUND_FOR_RESERVE.toString(), request.getIdsSeat().toString());
            //conf web socket decirle al cliente que no se encontraron asientos
            saveEventProcessed(request);
            return;
        }

        List<SeatModel> seatsBusy = seatToReserve.stream().filter(s -> s.getStatus().equals(StatusSeat.BUSY)
                || s.getStatus().equals(StatusSeat.DISABLE))
                .collect(Collectors.toList());

        if(!seatsBusy.isEmpty()){
            log.warn(MESSAGE_SEATS_SELECTED_IS_BUSY_OR_DISABLED + seatsBusy.toString());
            //web socket  decirle al cliente que los asientos que escogio estan ocupados
            saveEventProcessed(request);
            return;
        }

        seatToReserve.stream().forEach(s -> s.setStatus(StatusSeat.BUSY));

        seatRepository.saveAll(seatToReserve);
        log.info(MESSAGE_SEATS_RESERVED.toString());

        saveOutboxEvent(request,topicReservedSeats);
        saveEventProcessed(request);
    }
}
