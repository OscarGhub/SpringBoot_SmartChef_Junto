package com.oscar.proyecto.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@Embeddable
public class RecetaGuardadaId implements Serializable {

    private Integer id_usuario;

    private Integer id_receta;
}
