package com.oscar.proyecto.modelos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "lista_compra")
public class ListaCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonIgnore
    private Usuario usuario;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDate fecha_creacion = LocalDate.now();

    @OneToMany(mappedBy = "listaCompra")
    @JsonIgnore
    private Set<ListaCompraIngrediente> ingredientes;
}
