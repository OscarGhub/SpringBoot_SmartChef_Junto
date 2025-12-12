package com.oscar.proyecto.mapper;

import com.oscar.proyecto.dto.Coleccion.ColeccionResponseDTO;
import com.oscar.proyecto.modelos.Coleccion;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ColeccionMapper {

    public ColeccionResponseDTO toResponseDTO(Coleccion coleccion) {
        if (coleccion == null) {
            return null;
        }

        return new ColeccionResponseDTO(
                coleccion.getId(),
                coleccion.getNombre(),
                coleccion.getUsuario() != null ? coleccion.getUsuario().getId() : null
        );
    }

    public List<ColeccionResponseDTO> toResponseDTOList(List<Coleccion> colecciones) {
        return colecciones.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}