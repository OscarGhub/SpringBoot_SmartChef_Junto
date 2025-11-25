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

    public InventarioIngredienteRequestDTO(Integer idInventario, Integer idIngrediente, BigDecimal cantidad) {
        this.idInventario = idInventario;
        this.idIngrediente = idIngrediente;
        this.cantidad = cantidad;
    }
}
