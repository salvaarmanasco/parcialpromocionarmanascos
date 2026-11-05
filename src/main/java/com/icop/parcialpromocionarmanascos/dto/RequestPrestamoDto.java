package com.icop.parcialpromocionarmanascos.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestPrestamoDto {
    private Long socioId;
    private String tituloLibro;
    private LocalDate fechaPrestamo;

    public RequestPrestamoDto(Long socioId, String tituloLibro, LocalDate fechaPrestamo) {
        this.socioId = socioId;
        this.tituloLibro = tituloLibro;
        this.fechaPrestamo = fechaPrestamo;
    }
}
