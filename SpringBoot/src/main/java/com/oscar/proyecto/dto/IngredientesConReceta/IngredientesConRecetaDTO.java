package com.oscar.proyecto.dto.IngredientesConReceta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class IngredientesConRecetaDTO {
    private Integer idIngrediente;
    private String nombreIngrediente;
    private Double cantidad;
    private Integer idReceta;
    private String nombreReceta;
}
