package com.swSoftware.asientos.seat_ms.infrastructure.adapter.output.persistence;

import com.swSoftware.asientos.seat_ms.domain.model.OutboxEventModel;
import com.swSoftware.asientos.seat_ms.domain.status.StatusEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OutboxEventRepository extends JpaRepository<OutboxEventModel, UUID> {
    List<OutboxEventModel> findAllByStatus(StatusEvent status);
}