package com.oscar.proyecto.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Getter
@Entity
public class Receta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Integer id;

    @Setter
    private String titulo;

    @Setter
    private String descripcion;

    @Setter
    private String tutorial;

    @Setter
    private Integer tiempo_preparacion;

    @Setter
    private String foto_url;

    @Setter
    private Integer num_favoritos;

    @ManyToMany
    @JoinTable(
            name = "receta_preferencia",
            joinColumns = @JoinColumn(name = "id_receta"),
            inverseJoinColumns = @JoinColumn(name = "id_preferencia")
    )
    @JsonManagedReference
    private Set<Preferencia> preferencias;

}
