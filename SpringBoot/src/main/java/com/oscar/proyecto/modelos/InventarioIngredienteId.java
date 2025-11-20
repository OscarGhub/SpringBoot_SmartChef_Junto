package com.oscar.proyecto.modelos;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@Embeddable
public class InventarioIngredienteId implements Serializable {

    private Integer idInventario;
    private Integer idIngrediente;

    public InventarioIngredienteId() {
    }

    public InventarioIngredienteId(Integer idInventario, Integer idIngrediente) {
        this.idInventario = idInventario;
        this.idIngrediente = idIngrediente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InventarioIngredienteId)) return false;
        InventarioIngredienteId that = (InventarioIngredienteId) o;
        return Objects.equals(idInventario, that.idInventario) &&
                Objects.equals(idIngrediente, that.idIngrediente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idInventario, idIngrediente);
    }
}
