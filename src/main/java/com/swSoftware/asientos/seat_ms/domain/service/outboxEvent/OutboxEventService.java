package com.swSoftware.asientos.seat_ms.domain.service.outboxEvent;


import com.app.events.ReserveEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swSoftware.asientos.seat_ms.domain.model.OutboxEventModel;
import com.swSoftware.asientos.seat_ms.domain.port.IOutboxEventService;
import com.swSoftware.asientos.seat_ms.domain.status.StatusEvent;
import com.swSoftware.asientos.seat_ms.infrastructure.adapter.output.kafka.KafkaProducer;
import com.swSoftware.asientos.seat_ms.infrastructure.adapter.output.persistence.OutboxEventRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

import static com.swSoftware.asientos.seat_ms.domain.common.HeaderConstants.CORRELATION_KEY;
import static com.swSoftware.asientos.seat_ms.infrastructure.shared.LogMessages.*;

@Service
@AllArgsConstructor
@Slf4j
public class OutboxEventService<T> implements IOutboxEventService<T> {

    private final OutboxEventRepository outboxEventRepository;
    private final KafkaProducer kafkaProducer;
    private final ObjectMapper objectMapper;

    @Override
    public void saveEvent(T request,String nameTopic){
        String payloadJson = request.toString();
        outboxEventRepository.save(OutboxEventModel.builder()
                .correlationId(MDC.get(CORRELATION_KEY.toString()))
                .createAt(Instant.now())
                .nameTopic(nameTopic)
                .status(StatusEvent.PENDING)
                .retryCount(0)
                .payload(payloadJson)
                .build());

        log.info(MESSAGE_EVENT_SAVED.toString());
    }

    @Scheduled(fixedDelay = 500)
    public void publishPendingEvents() {
        List<OutboxEventModel> events = outboxEventRepository.findAllByStatus(StatusEvent.PENDING);

        for (OutboxEventModel e : events) {
            try {
                ReserveEvent event = objectMapper.readValue(e.getPayload(), ReserveEvent.class);

                kafkaProducer.send(event,e.getNameTopic(), e.getCorrelationId());
                e.setStatus(StatusEvent.SENT);
                outboxEventRepository.save(e);
                log.info(MESSAGE_SEND_EVENT.toString(), e.getId());

            } catch (Exception ex){

                e.setRetryCount(e.getRetryCount() + 1);
                if (e.getRetryCount() > 20) {
                    try{
                        ReserveEvent event = objectMapper.readValue(e.getPayload(), ReserveEvent.class);
                        e.setStatus(StatusEvent.FAILED);
                        kafkaProducer.publisFailedSendEventDlq(event);
                    }catch (Exception exc){
                        log.warn("{}: {}", MESSAGE_ERROR_SEND_EVENT, exc.getMessage());
                    }
                }

                outboxEventRepository.save(e);
            }
        }
    }
}