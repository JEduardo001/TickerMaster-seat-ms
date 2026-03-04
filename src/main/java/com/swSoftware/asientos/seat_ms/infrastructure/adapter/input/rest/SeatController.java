package com.swSoftware.asientos.seat_ms.infrastructure.adapter.input.rest;

import com.swSoftware.asientos.seat_ms.application.dto.responseApi.DtoResponseApi;
import com.swSoftware.asientos.seat_ms.application.dto.seat.DtoUpdateSeat;
import com.swSoftware.asientos.seat_ms.application.usecase.GetAllSeatsUseCase;
import com.swSoftware.asientos.seat_ms.application.usecase.GetSeatUseCase;
import com.swSoftware.asientos.seat_ms.application.usecase.UpdateSeatUseCase;
import com.swSoftware.asientos.seat_ms.domain.model.SeatModel;
import com.swSoftware.asientos.seat_ms.domain.status.SectionSeat;
import com.swSoftware.asientos.seat_ms.domain.status.StatusSeat;
import com.swSoftware.asientos.seat_ms.infrastructure.adapter.output.persistence.SeatRepository;
import io.swagger.v3.oas.annotations.Parameter;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static com.swSoftware.asientos.seat_ms.domain.common.HeaderConstants.CORRELATION_KEY;

@RestController
@RequestMapping("/api/v1/seat")
@AllArgsConstructor
public class SeatController {

    private final UpdateSeatUseCase updateSeatUseCase;
    private final GetSeatUseCase getSeatUseCase;
    private final GetAllSeatsUseCase getAllSeatsUseCase;
    private final SeatRepository seatRepository;

    //this endpoint is only to create seats to test
    @PostMapping()
    public ResponseEntity<DtoResponseApi> setsSeats(){
        List<SectionSeat> sections = List.of(SectionSeat.A,SectionSeat.B,SectionSeat.C,SectionSeat.D,
                SectionSeat.E,SectionSeat.F,SectionSeat.G,SectionSeat.H);
        int fila = 1;
        int seatNumber = 1;
        int cantSeat = 1;
        for(int i = 0;i<8;i++){

            for(int i2 = 0;i2<20;i2++){
                seatRepository.save(SeatModel.builder()
                                .row(fila)
                                .section(sections.get(i))
                                .seatNumber(seatNumber)
                                .status(StatusSeat.AVAILABLE)
                                .createAt(Instant.now())
                        .build());
                cantSeat ++;
                seatNumber++;
                if(cantSeat == 6){
                    fila++;
                    cantSeat = 1;
                }
            }
            fila = 1;
            cantSeat = 1;
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(DtoResponseApi.builder()
                .status(HttpStatus.CREATED.value())
                .message("Seat created")
                .idCorrelation(MDC.get(CORRELATION_KEY.toString()))
                .data(null)
                .build()
        );
    }

    @PutMapping()
    public ResponseEntity<DtoResponseApi> updateSeat(@Valid @RequestBody DtoUpdateSeat request){
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoResponseApi.builder()
                .status(HttpStatus.CREATED.value())
                .message("Seat updated")
                .idCorrelation(MDC.get(CORRELATION_KEY.toString()))
                .data(updateSeatUseCase.execute(request))
                .build()
        );
    }

    @GetMapping("/{idSeat}")
    public ResponseEntity<DtoResponseApi> getSeat(@Parameter(description = "UUID of the seat") @PathVariable UUID idSeat){
        return ResponseEntity.status(HttpStatus.OK).body(DtoResponseApi.builder()
                .status(HttpStatus.OK.value())
                .idCorrelation(MDC.get(CORRELATION_KEY.toString()))
                .message("Seat obtained")
                .data(getSeatUseCase.execute(idSeat))
                .build()
        );
    }

    @GetMapping()
    public ResponseEntity<DtoResponseApi> getAllSeat(
            @Parameter(description = "UUID cursor for pagination") @RequestParam(required = false) UUID lastId,
            @Parameter(description = "Page size limit") @RequestParam(defaultValue = "160") int limit
    ) {
        return ResponseEntity.ok(DtoResponseApi.builder()
                .status(HttpStatus.OK.value())
                .idCorrelation(MDC.get(CORRELATION_KEY.toString()))
                .message("Seats obtained")
                .data(getAllSeatsUseCase.execute(lastId, limit))
                .build());
    }
}
