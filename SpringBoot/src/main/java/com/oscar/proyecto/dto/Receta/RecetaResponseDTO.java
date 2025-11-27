package com.oscar.proyecto.dto.Receta;

import com.oscar.proyecto.modelos.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecetaResponseDTO {
    private Integer id;
    private String titulo;
    private String descripcion;
    private String tutorial;
    private Integer tiempoPreparacion;
    private String fotoUrl;
    private Integer numFavoritos;
    private Boolean guardada;
    private List<Usuario> usuariosQueGuardaron;
}
