package com.oscar.proyecto.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "receta_guardada")
public class RecetaGuardada {

    @EmbeddedId
    private RecetaGuardadaId id;

    @ManyToOne
    @MapsId("id_receta")
    @JoinColumn(name = "id_receta")
    private Receta receta;

     @ManyToOne
     @MapsId("id_usuario")
     @JoinColumn(name = "id_usuario")
     private Usuario usuario;

}
