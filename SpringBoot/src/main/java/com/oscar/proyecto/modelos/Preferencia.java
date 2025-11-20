package com.oscar.proyecto.modelos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "preferencia")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Preferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(length = 100, nullable = false)
    private String nombre;

    @ManyToMany(mappedBy = "preferencias")
    @JsonBackReference
    private Set<Receta> recetas;
}
