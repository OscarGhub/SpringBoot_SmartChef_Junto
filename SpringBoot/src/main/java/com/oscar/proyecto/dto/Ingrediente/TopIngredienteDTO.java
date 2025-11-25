package com.oscar.proyecto.dto.Ingrediente;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopIngredienteDTO {

    private String ingrediente;

    private Long vecesUtilizado;

    public TopIngredienteDTO() {
    }

    public TopIngredienteDTO(String ingrediente, Long vecesUtilizado) {
        this.ingrediente = ingrediente;
        this.vecesUtilizado = vecesUtilizado;
    }
}