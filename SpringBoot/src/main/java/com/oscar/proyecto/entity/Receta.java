package com.oscar.proyecto.entity;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Receta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter @Setter
    private String titulo;

    @Getter @Setter
    private String descripcion;

    @Getter @Setter
    private Integer tiempoPreparacion;

    @Getter @Setter
    private String fotoUrl;

    @Getter @Setter
    private Integer numFavoritos;

}
