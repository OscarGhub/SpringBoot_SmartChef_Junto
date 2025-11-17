package com.oscar.proyecto.dto.Ingrediente;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngredienteResponseDTO {
    private Integer id;
    private String nombre;
    private String unidadMedida;
    private String imagenUrl;

    public IngredienteResponseDTO(Integer id, String nombre, String unidadMedida, String imagenUrl) {
        this.id = id;
        this.nombre = nombre;
        this.unidadMedida = unidadMedida;
        this.imagenUrl = imagenUrl;
    }
}
