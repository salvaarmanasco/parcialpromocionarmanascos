package com.icop.parcialpromocionarmanascos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseSocioDto {
    private String nombreCompleto;
    private String dni;

    public ResponseSocioDto(String nombre, String apellido, String dni) {
        this.nombreCompleto = nombre + " " + apellido;
        this.dni = dni;
    }
}
