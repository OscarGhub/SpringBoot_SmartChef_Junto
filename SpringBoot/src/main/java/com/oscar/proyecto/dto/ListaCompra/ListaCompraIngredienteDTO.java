package com.oscar.proyecto.dto.ListaCompra;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListaCompraIngredienteDTO {

    private IngredienteEnCarritoDTO ingrediente;
    private Double cantidad;
}