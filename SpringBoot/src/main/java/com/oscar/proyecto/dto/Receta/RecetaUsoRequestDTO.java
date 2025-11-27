package com.oscar.proyecto.dto.Receta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecetaUsoRequestDTO {
    private Integer idReceta;
    private Integer idUsuario;
    private LocalDate fecha;
}
