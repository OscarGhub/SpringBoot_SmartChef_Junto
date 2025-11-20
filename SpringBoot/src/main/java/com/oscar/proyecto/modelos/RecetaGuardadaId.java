package com.oscar.proyecto.modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class RecetaGuardadaId implements Serializable {

    @Column(name = "id_usuario")
    private Integer id_usuario;

    @Column(name = "id_receta")
    private Integer id_receta;

    public RecetaGuardadaId() {}

    public RecetaGuardadaId(Integer id_usuario, Integer id_receta) {
        this.id_usuario = id_usuario;
        this.id_receta = id_receta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecetaGuardadaId)) return false;
        RecetaGuardadaId that = (RecetaGuardadaId) o;
        return Objects.equals(id_usuario, that.id_usuario) &&
                Objects.equals(id_receta, that.id_receta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_usuario, id_receta);
    }
}
