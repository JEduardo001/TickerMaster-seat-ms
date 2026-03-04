package com.swSoftware.asientos.seat_ms.domain.model;

import com.swSoftware.asientos.seat_ms.domain.status.SectionSeat;
import com.swSoftware.asientos.seat_ms.domain.status.StatusSeat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "seat_table")
public class SeatModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Integer row;
    @Enumerated(EnumType.STRING)
    private SectionSeat section;
    private Integer seatNumber;
    @Enumerated(EnumType.STRING)
    private StatusSeat status;
    private Instant createAt;

}
