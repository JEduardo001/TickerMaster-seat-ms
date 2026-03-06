package com.swSoftware.asientos.seat_ms.domain.model;

import com.swSoftware.asientos.seat_ms.domain.status.StatusEvent;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "outboxEvent_table")
public class OutboxEventModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String correlationId;
    private String nameTopic;
    @Column(columnDefinition = "TEXT")
    private String payload;
    @Enumerated(EnumType.STRING)
    private StatusEvent status;
    private Integer retryCount;
    private Instant createAt;

}
