package com.swSoftware.asientos.seat_ms.infrastructure.config.kafka;


import com.swSoftware.asientos.seat_ms.domain.common.HeaderConstants;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.MDC;
import org.springframework.kafka.listener.RecordInterceptor;
import org.springframework.stereotype.Component;

@Component
public class CorrelationIdInterceptor implements RecordInterceptor<String, Object> {

    @Override
    public ConsumerRecord<String, Object> intercept(ConsumerRecord<String, Object> record, Consumer<String, Object> consumer) {
        var header = record.headers().lastHeader(HeaderConstants.CORRELATION_HEADER.toString());

        String correlationId;
        if (header != null && header.value() != null) {
            correlationId = new String(header.value());
        } else {
            correlationId = java.util.UUID.randomUUID().toString();
        }

        MDC.put(HeaderConstants.CORRELATION_KEY.toString(), correlationId);

        return record;
    }

    @Override
    public void afterRecord(ConsumerRecord<String, Object> record, Consumer<String, Object> consumer) {
        MDC.remove(HeaderConstants.CORRELATION_KEY.toString());
    }
}
