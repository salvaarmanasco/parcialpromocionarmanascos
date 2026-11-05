package com.icop.parcialpromocionarmanascos.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.icop.parcialpromocionarmanascos.dto.RequestPrestamoDto;
import com.icop.parcialpromocionarmanascos.dto.ResponsePrestamoDto;
import com.icop.parcialpromocionarmanascos.exception.OperacionNoPermitidaException;
import com.icop.parcialpromocionarmanascos.exception.RecursoInexistenteException;
import com.icop.parcialpromocionarmanascos.service.PrestamoService;






@RestController 
@RequestMapping("/prestamos")
public class PrestamoController {
private final PrestamoService prestamoService;

public PrestamoController(PrestamoService prestamoService) {
    this.prestamoService = prestamoService;
}

@PostMapping
public ResponseEntity<?> crearPrestamo(@RequestBody RequestPrestamoDto request) {
    try {
        ResponsePrestamoDto respuesta = prestamoService.crearPrestamo(request);
        return ResponseEntity.status(201).body(respuesta);
    } catch (RecursoInexistenteException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    } catch (OperacionNoPermitidaException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    } catch (Exception e) {
        return ResponseEntity.internalServerError().body("Error interno del servidor");
    }
}

@GetMapping("/socio/{id}")
public ResponseEntity<?> listarPorSocio(@PathVariable Long id) {
    try {
        List<ResponsePrestamoDto> respuesta = prestamoService.listarPrestamosPorSocio(id);
        return ResponseEntity.ok(respuesta);
    } catch (RecursoInexistenteException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    } catch (Exception e) {
        return ResponseEntity.internalServerError().body("Error interno del servidor");
    }
}
}




