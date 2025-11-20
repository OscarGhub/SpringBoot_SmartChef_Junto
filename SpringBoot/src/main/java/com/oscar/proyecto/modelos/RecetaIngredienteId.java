package com.oscar.proyecto.modelos;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class RecetaIngredienteId implements Serializable {
    private Integer idReceta;
    private Integer idIngrediente;

    public RecetaIngredienteId() {}
    public RecetaIngredienteId(Integer idReceta, Integer idIngrediente) {
        this.idReceta = idReceta;
        this.idIngrediente = idIngrediente;
    }
}
