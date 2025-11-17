package com.oscar.proyecto.dto.Receta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecetaResponseDTO {
    private Integer id;
    private String titulo;
    private String descripcion;
    private String tutorial;
    private Integer tiempoPreparacion;
    private String fotoUrl;
    private Integer numFavoritos;
    private Boolean guardada;
}
