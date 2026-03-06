package com.swSoftware.asientos.seat_ms.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "eventProcessed_table")
public class EventProcessedModel {

    @Id
    private UUID id;
    @Column(columnDefinition = "TEXT")
    private String data;
    private Instant createdAt;
}

