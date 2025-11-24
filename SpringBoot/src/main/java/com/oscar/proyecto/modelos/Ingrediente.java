package com.oscar.proyecto.modelos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Ingrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(name = "unidad_medida", length = 50)
    private String unidadMedida;

    @Column(name = "imagen_url")
    private String imagenUrl;
}
