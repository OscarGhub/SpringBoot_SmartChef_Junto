package com.oscar.proyecto.modelos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "inventario_ingrediente")
@Getter
@Setter
public class InventarioIngrediente {

    @EmbeddedId
    private InventarioIngredienteId id;

    @ManyToOne
    @MapsId("idInventario")
    @JoinColumn(name="id_inventario")
    private Inventario inventario;

    @ManyToOne
    @MapsId("idIngrediente")
    @JoinColumn(name="id_ingrediente")
    private Ingrediente ingrediente;

    @Column(precision = 10, scale = 2)
    private BigDecimal cantidad;
}
