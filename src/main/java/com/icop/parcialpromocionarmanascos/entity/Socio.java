package com.icop.parcialpromocionarmanascos.entity;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity 
@Table(name = "socio")
@Getter 
@Setter 
public class Socio {
@Id 
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id")
private Long id;
private String nombre;
private String apellido;
private String dni;


@OneToMany(mappedBy = "socio")
private List<Prestamo> prestamos = new ArrayList<>();
}


