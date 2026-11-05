package com.icop.parcialpromocionarmanascos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.icop.parcialpromocionarmanascos.entity.Prestamo;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    boolean existsByTituloLibroAndDevueltoFalse(String tituloLibro);
}
