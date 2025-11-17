package com.oscar.proyecto.dto.Receta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecetaRequestDTO {
    private String titulo;
    private String descripcion;
    private String tutorial;
    private Integer tiempoPreparacion;
    private String fotoUrl;
}
