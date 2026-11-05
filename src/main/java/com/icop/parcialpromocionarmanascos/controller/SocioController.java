package com.icop.parcialpromocionarmanascos.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.icop.parcialpromocionarmanascos.dto.RequestSocioDto;
import com.icop.parcialpromocionarmanascos.dto.ResponseSocioDto;
import com.icop.parcialpromocionarmanascos.exception.OperacionNoPermitidaException;
import com.icop.parcialpromocionarmanascos.exception.RecursoInexistenteException;
import com.icop.parcialpromocionarmanascos.service.SocioService;



@RestController
@RequestMapping("/socios")
public class SocioController {
    private final SocioService socioService;

    public SocioController(SocioService socioService) {
        this.socioService = socioService;
    }

  
      @GetMapping("")
    public ResponseEntity<?> getSocios() {
        try {
            List<ResponseSocioDto> respuesta = socioService.listarSocios();
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al listar socios.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSocioById(@PathVariable Long id) {
        try {
            ResponseSocioDto socio = socioService.obtenerSocioPorId(id);
            return ResponseEntity.ok(socio);
        } catch (RecursoInexistenteException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al obtener socio por ID.");
        }
    }


    @PostMapping("")
    public ResponseEntity<?> postSocio(@RequestBody RequestSocioDto request) {
        try {
            ResponseSocioDto respuesta = socioService.crearSocio(request);
            return ResponseEntity.status(201).body(respuesta);
        } catch (OperacionNoPermitidaException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno del servidor");
        }
    }


@DeleteMapping("/{id}")
public ResponseEntity<?> deleteSocio(@PathVariable Long id) {
    try {
        socioService.eliminarSocio(id);
        return ResponseEntity.noContent().build();
    } catch (RecursoInexistenteException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    } catch (OperacionNoPermitidaException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
        return ResponseEntity.internalServerError().body("Error interno del servidor");
    }
}

}
