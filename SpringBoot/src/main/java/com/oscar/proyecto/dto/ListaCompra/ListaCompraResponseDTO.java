package com.oscar.proyecto.dto.ListaCompra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class ListaCompraResponseDTO {
    private Integer idListaCompra;
    private Integer idUsuario;
    private LocalDate fechaCreacion;
}
