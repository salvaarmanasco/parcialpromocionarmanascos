package com.icop.parcialpromocionarmanascos.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icop.parcialpromocionarmanascos.dto.RequestSocioDto;
import com.icop.parcialpromocionarmanascos.dto.ResponseSocioDto;
import com.icop.parcialpromocionarmanascos.entity.Socio;
import com.icop.parcialpromocionarmanascos.exception.OperacionNoPermitidaException;
import com.icop.parcialpromocionarmanascos.exception.RecursoInexistenteException;
import com.icop.parcialpromocionarmanascos.repository.SocioRepository;

@Service
public class SocioService {
    
    private final SocioRepository repository;

    public SocioService(SocioRepository repository) {
    this.repository = repository;
    }
 
    public List<ResponseSocioDto> listarSocios() {
        List<Socio> socios = repository.findAll();
        List<ResponseSocioDto> respuesta = new ArrayList<>();

        for (Socio socio : socios) {
            respuesta.add(convertirResponseSocioDto(socio));
        }

        return respuesta;
    }
  

    public ResponseSocioDto obtenerSocioPorId(Long id) {
        Socio socio = repository.findById(id)
                .orElseThrow(() -> new RecursoInexistenteException("Socio no encontrado con id: " + id));
        return convertirResponseSocioDto(socio);
    }
         
    public ResponseSocioDto crearSocio (RequestSocioDto request) {
        if (repository.existsByDni(request.getDni())) {
            throw new OperacionNoPermitidaException(
                    "Ya existe un socio con el DNI: " + request.getDni());
        }

        Socio nuevoSocio = new Socio();
        nuevoSocio.setNombre(request.getNombre());
        nuevoSocio.setApellido(request.getApellido());
        nuevoSocio.setDni(request.getDni());

        Socio socioGuardado = repository.save(nuevoSocio);
        return convertirResponseSocioDto(socioGuardado);
    }


    @Transactional
    public void eliminarSocio(Long id) {
        Socio socio = repository.findById(id)
                .orElseThrow(() -> new RecursoInexistenteException(
                        "Socio no encontrado con id: " + id));

        if (!socio.getPrestamos().isEmpty()) {
            throw new OperacionNoPermitidaException(
                    "No se puede eliminar el socio con id: " + id
                            + " porque tiene préstamos asociados");
        }

        repository.delete(socio);
    }


    private ResponseSocioDto convertirResponseSocioDto(Socio socio) {
        return new ResponseSocioDto(socio.getNombre(), socio.getApellido(), socio.getDni());
    }
}


