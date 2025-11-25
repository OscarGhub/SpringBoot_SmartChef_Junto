package com.oscar.proyecto.mapper;

import com.oscar.proyecto.dto.Inventario.InventarioResponseDTO;
import com.oscar.proyecto.modelos.Inventario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InventarioMapper {

    @Mapping(target = "idInventario", source = "id")
    @Mapping(target = "usuarioId", source = "usuario.id")

    InventarioResponseDTO toResponseDTO(Inventario inventario);

    List<InventarioResponseDTO> toResponseDTOList(List<Inventario> inventarios);
}