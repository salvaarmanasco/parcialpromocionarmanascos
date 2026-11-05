package com.icop.parcialpromocionarmanascos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.icop.parcialpromocionarmanascos.entity.Socio;

public interface SocioRepository extends JpaRepository<Socio, Long> {
    boolean existsByDni(String dni);
}
