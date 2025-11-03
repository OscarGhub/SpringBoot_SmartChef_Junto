package com.oscar.proyecto.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@Embeddable
public class RecetaGuardadaId implements Serializable {

    private Integer id_usuario;
    private Integer id_receta;

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
