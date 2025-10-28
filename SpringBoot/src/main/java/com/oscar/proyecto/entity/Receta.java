package com.oscar.proyecto.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Receta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Integer id;

    @Getter @Setter
    private String titulo;

    @Getter @Setter
    private String descripcion;

    @Getter @Setter
    private String tutorial;

    @Getter @Setter
    private Integer tiempo_preparacion;

    @Getter @Setter
    private String foto_url;

    @Getter @Setter
    private Integer num_favoritos;

}
