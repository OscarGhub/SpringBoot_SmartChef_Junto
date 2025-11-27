package com.oscar.proyecto.modelos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "receta_guardada")
public class RecetaGuardada {

    @EmbeddedId
    private RecetaGuardadaId id;

    @ManyToOne
    @MapsId("idReceta")
    @JoinColumn(name = "id_receta", nullable = false)
    private Receta receta;

    @ManyToOne
    @MapsId("idUsuario")
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonIgnore
    private Usuario usuario;

    @Column(name = "fecha_guardado", nullable = false)
    private LocalDateTime fechaGuardado = LocalDateTime.now();

    public RecetaGuardada() {}
}
