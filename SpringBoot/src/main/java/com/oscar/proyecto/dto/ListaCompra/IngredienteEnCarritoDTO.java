package com.oscar.proyecto.dto.ListaCompra;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngredienteEnCarritoDTO {
    private Integer id;
    private String nombre;
    private String unidadMedida;
    private String imagenUrl;
}