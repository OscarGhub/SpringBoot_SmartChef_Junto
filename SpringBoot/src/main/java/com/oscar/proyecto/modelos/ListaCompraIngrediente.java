package com.oscar.proyecto.modelos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "lista_compra_ingrediente")
public class ListaCompraIngrediente {

    @EmbeddedId
    private ListaCompraIngredienteId id;

    @ManyToOne
    @MapsId("idLista")
    @JoinColumn(name = "id_lista")
    private ListaCompra listaCompra;

    @ManyToOne
    @MapsId("idIngrediente")
    @JoinColumn(name = "id_ingrediente")
    private Ingrediente ingrediente;

    @Column(name = "cantidad")
    private Double cantidad;

    public ListaCompraIngrediente() {}

    public ListaCompraIngrediente(ListaCompra listaCompra, Ingrediente ingrediente, Double cantidad) {
        this.listaCompra = listaCompra;
        this.ingrediente = ingrediente;
        this.cantidad = cantidad;
        this.id = new ListaCompraIngredienteId(listaCompra.getId(), ingrediente.getId());
    }
}
