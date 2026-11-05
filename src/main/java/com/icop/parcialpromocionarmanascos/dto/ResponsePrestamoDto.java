package com.icop.parcialpromocionarmanascos.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponsePrestamoDto {
    private Long id;
    private String tituloLibro;
    private LocalDate fechaPrestamo;
    private boolean devuelto;
    private String nombreSocio;

    public ResponsePrestamoDto(Long id, String tituloLibro, LocalDate fechaPrestamo,
                               boolean devuelto, String nombreSocio) {
        this.id = id;
        this.tituloLibro = tituloLibro;
        this.fechaPrestamo = fechaPrestamo;
        this.devuelto = devuelto;
        this.nombreSocio = nombreSocio;
    }
}
