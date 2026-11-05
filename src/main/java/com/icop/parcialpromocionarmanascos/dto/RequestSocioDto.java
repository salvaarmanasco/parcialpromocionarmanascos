package com.icop.parcialpromocionarmanascos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestSocioDto {
    private String nombre;
    private String apellido;
    private String dni;


    public RequestSocioDto(String nombre, String apellido, String dni) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
    }
}
