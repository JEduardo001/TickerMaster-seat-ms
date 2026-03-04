package com.swSoftware.asientos.seat_ms.infrastructure.adapter.output.persistence;

import com.swSoftware.asientos.seat_ms.domain.model.SeatModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SeatRepository extends JpaRepository<SeatModel, UUID> {
    @Query("SELECT u FROM SeatModel u WHERE (:lastId IS NULL OR u.id > :lastId) ORDER BY u.id ASC")
    List<SeatModel> findNextPage(@Param("lastId") UUID lastId, Pageable pageable);
}
