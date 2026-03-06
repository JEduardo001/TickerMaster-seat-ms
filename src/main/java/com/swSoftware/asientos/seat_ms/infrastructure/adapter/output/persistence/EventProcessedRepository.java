package com.swSoftware.asientos.seat_ms.infrastructure.adapter.output.persistence;

import com.swSoftware.asientos.seat_ms.domain.model.EventProcessedModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventProcessedRepository extends JpaRepository<EventProcessedModel, UUID> {
}

