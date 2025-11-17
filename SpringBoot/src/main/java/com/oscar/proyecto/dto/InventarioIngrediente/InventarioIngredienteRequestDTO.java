package com.oscar.proyecto.dto.InventarioIngrediente;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class InventarioIngredienteRequestDTO {
    private Integer idInventario;
    private Integer idIngrediente;
    private BigDecimal cantidad;
}
