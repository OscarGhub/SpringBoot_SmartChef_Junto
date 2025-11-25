package com.oscar.proyecto.modelos;

public class RecetaCocinadaFechaId {

    private Integer idUsuario;
    private Integer idReceta;

    public RecetaCocinadaFechaId(){}

    public RecetaCocinadaFechaId(Integer idUsuario, Integer idReceta) {
        this.idUsuario = idUsuario;
        this.idReceta = idReceta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecetaCocinadaFechaId that)) return false;
        return idUsuario.equals(that.idUsuario) && idReceta.equals(that.idReceta);
    }

    @Override
    public int hashCode() {
        return idUsuario.hashCode() + idReceta.hashCode();
    }
}
