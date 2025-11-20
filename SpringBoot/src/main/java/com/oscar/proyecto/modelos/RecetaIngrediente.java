package com.oscar.proyecto.modelos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "receta_ingrediente")
public class RecetaIngrediente {

    @EmbeddedId
    private RecetaIngredienteId id;

    @ManyToOne
    @MapsId("idReceta")
    @JoinColumn(name = "id_receta")
    private Receta receta;

    @ManyToOne
    @MapsId("idIngrediente")
    @JoinColumn(name = "id_ingrediente")
    private Ingrediente ingrediente;

    @Column(name = "cantidad")
    private Double cantidad;

    public RecetaIngrediente() {}

    public RecetaIngrediente(Receta receta, Ingrediente ingrediente, Double cantidad) {
        this.receta = receta;
        this.ingrediente = ingrediente;
        this.cantidad = cantidad;
        this.id = new RecetaIngredienteId(receta.getId(), ingrediente.getId());
    }
}
