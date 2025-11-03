package com.oscar.proyecto.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class ListaCompra {
    Integer id;
    Integer id_usuario;
    LocalDate fecha_creacion;
}
