package com.icop.parcialpromocionarmanascos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icop.parcialpromocionarmanascos.dto.RequestPrestamoDto;
import com.icop.parcialpromocionarmanascos.dto.ResponsePrestamoDto;
import com.icop.parcialpromocionarmanascos.entity.Prestamo;
import com.icop.parcialpromocionarmanascos.entity.Socio;
import com.icop.parcialpromocionarmanascos.exception.OperacionNoPermitidaException;
import com.icop.parcialpromocionarmanascos.exception.RecursoInexistenteException;
import com.icop.parcialpromocionarmanascos.repository.PrestamoRepository;
import com.icop.parcialpromocionarmanascos.repository.SocioRepository;



@Service
public class PrestamoService {
   private final PrestamoRepository repository;
   private final SocioRepository socioRepository;

    public PrestamoService(PrestamoRepository repository, SocioRepository socioRepository) {
        this.repository = repository;
        this.socioRepository = socioRepository;
    }
 
    @Transactional
    public ResponsePrestamoDto crearPrestamo (RequestPrestamoDto request) {
        Socio socio = socioRepository.findById(request.getSocioId())
                .orElseThrow(() -> new RecursoInexistenteException(
                        "Socio no encontrado con id: " + request.getSocioId()));

        if (repository.existsByTituloLibroAndDevueltoFalse(request.getTituloLibro())) {
            throw new OperacionNoPermitidaException(
                    "El libro " + request.getTituloLibro() + " ya se encuentra prestado");
        }

        Prestamo nuevoPrestamo = new Prestamo();
        nuevoPrestamo.setSocio(socio);
        nuevoPrestamo.setTituloLibro(request.getTituloLibro());
        nuevoPrestamo.setFechaPrestamo(request.getFechaPrestamo());
        nuevoPrestamo.setDevuelto(false);

        Prestamo prestamoGuardado = repository.save(nuevoPrestamo);
        return convertirResponsePrestamoDto(prestamoGuardado);
    }

    @Transactional(readOnly = true)
    public List<ResponsePrestamoDto> listarPrestamosPorSocio(Long id) {
        Socio socio = socioRepository.findById(id)
                .orElseThrow(() -> new RecursoInexistenteException(
                        "Socio no encontrado con id: " + id));
        List<ResponsePrestamoDto> respuesta = new ArrayList<>();
        for (Prestamo prestamo : socio.getPrestamos()) {
            respuesta.add(convertirResponsePrestamoDto(prestamo));
        }
        return respuesta;
    }

    private ResponsePrestamoDto convertirResponsePrestamoDto(Prestamo prestamo) {
        Socio socio = prestamo.getSocio();
        String nombreSocio = socio.getNombre() + " " + socio.getApellido();
        return new ResponsePrestamoDto(prestamo.getId(), prestamo.getTituloLibro(),
                prestamo.getFechaPrestamo(), prestamo.isDevuelto(), nombreSocio);
    }

}
