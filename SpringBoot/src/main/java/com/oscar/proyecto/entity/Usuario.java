package com.oscar.proyecto.entity;

import jakarta.persistence.*;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    @Column(unique = true)
    private String correoElectronico;

    private String contrasena;

    // Getters y setters...
}
