package com.oscar.proyecto.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Integer id;

    @Getter @Setter
    private String nombre;

    @Column(unique = true)
    @Getter @Setter
    private String correo_electronico;

    @Getter @Setter
    private String contrasena;
}
