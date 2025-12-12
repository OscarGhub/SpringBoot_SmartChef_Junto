package com.oscar.proyecto.dto.Coleccion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ColeccionResponseDTO {
    private Integer id;
    private String nombre;
    private Integer idUsuario;
}