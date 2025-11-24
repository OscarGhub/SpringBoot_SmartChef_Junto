package com.oscar.proyecto.dto.ListaCompra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredienteEnCarritoDTO {

    private Integer id;
    private String nombre;
    private String unidadMedida;
    private String imagenUrl;

}