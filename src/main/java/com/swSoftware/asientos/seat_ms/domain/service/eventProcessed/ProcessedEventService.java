package com.swSoftware.asientos.seat_ms.domain.service.eventProcessed;

import com.swSoftware.asientos.seat_ms.domain.model.EventProcessedModel;
import com.swSoftware.asientos.seat_ms.domain.port.IEventProcessedService;
import com.swSoftware.asientos.seat_ms.infrastructure.adapter.output.persistence.EventProcessedRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.swSoftware.asientos.seat_ms.infrastructure.shared.LogMessages.MESSAGE_EVENT_PROCESSED_SAVED;

@Service
@AllArgsConstructor
@Slf4j
public class ProcessedEventService implements IEventProcessedService {

    private final EventProcessedRepository eventProcessedRepository;

    @Override
    public void saveEventProcessed(EventProcessedModel request){
        eventProcessedRepository.save(request);
        log.info(MESSAGE_EVENT_PROCESSED_SAVED.toString());

    }

    @Override
    public boolean eventAlreadyProcessed(UUID id){
        return eventProcessedRepository.existsById(id);
    }
}
