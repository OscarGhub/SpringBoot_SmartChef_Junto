package com.oscar.proyecto.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Inventario {
    @Id
    private Integer id;

    @ManyToOne
    @JoinColumn(name="id_usuario")
    private Usuario usuario;

    @OneToMany(mappedBy="inventario")
    private List<InventarioIngrediente> inventarioIngredientes;
}
