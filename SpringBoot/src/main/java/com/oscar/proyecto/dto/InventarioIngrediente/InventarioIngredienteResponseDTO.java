package com.oscar.proyecto.dto.InventarioIngrediente;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class InventarioIngredienteResponseDTO {

    private Integer idInventario;
    private Integer idIngrediente;
    private Integer usuarioId;
    private String nombre;
    private String unidadMedida;
    private String imagenUrl;
    private BigDecimal cantidad;

}
