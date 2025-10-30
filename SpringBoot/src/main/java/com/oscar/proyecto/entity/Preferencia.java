package com.oscar.proyecto.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Getter
@Entity
public class Preferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Integer id;

    @Setter
    private String nombre;

    @ManyToMany(mappedBy = "preferencias")
    @JsonBackReference
    private Set<Receta> recetas;
}
