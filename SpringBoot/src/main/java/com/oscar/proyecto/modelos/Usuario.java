package com.oscar.proyecto.modelos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100, nullable = false)
    private String nombre;

    @Column(name = "correo_electronico", unique = true, nullable = false, length = 150)
    private String correoElectronico;

    @Column(nullable = false, length = 255)
    private String contrasena;

    private LocalDate fechaNacimiento;

    private String fotoUrl;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<ListaCompra> listasCompra;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<RecetaGuardada> recetasGuardadas;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Inventario inventario;

    public Usuario() {}
}
