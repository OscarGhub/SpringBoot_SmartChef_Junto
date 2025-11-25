package com.oscar.proyecto.modelos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "historial_cocinado")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RecetaCocinadaFecha {

    @EmbeddedId
    private InventarioIngredienteId id;

    @ManyToOne
    @MapsId("idUsuario")
    @JoinColumn(name="id_usuario")
    private Usuario usuario;

    @ManyToOne
    @MapsId("idReceta")
    @JoinColumn(name="id_receta")
    private Receta receta;

    private String fecha_cocinado;
}