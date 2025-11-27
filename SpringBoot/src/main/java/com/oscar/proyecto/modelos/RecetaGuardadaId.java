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
    private Integer idUsuario;

    @Column(name = "id_receta")
    private Integer idReceta;

    public RecetaGuardadaId() {}

    public RecetaGuardadaId(Integer idUsuario, Integer idReceta) {
        this.idUsuario = idUsuario;
        this.idReceta = idReceta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecetaGuardadaId)) return false;
        RecetaGuardadaId that = (RecetaGuardadaId) o;
        return Objects.equals(idUsuario, that.idUsuario) &&
                Objects.equals(idReceta, that.idReceta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, idReceta);
    }
}
