package com.oscar.proyecto.modelos;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class ListaCompraIngredienteId implements Serializable {
    private Integer idLista;
    private Integer idIngrediente;

    public ListaCompraIngredienteId() {}

    public ListaCompraIngredienteId(Integer idLista, Integer idIngrediente) {
        this.idLista = idLista;
        this.idIngrediente = idIngrediente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListaCompraIngredienteId)) return false;
        ListaCompraIngredienteId that = (ListaCompraIngredienteId) o;
        return idLista.equals(that.idLista) && idIngrediente.equals(that.idIngrediente);
    }

    @Override
    public int hashCode() {
        return idLista.hashCode() + idIngrediente.hashCode();
    }
}
