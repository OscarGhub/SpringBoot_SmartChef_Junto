package com.oscar.proyecto.mapper;

import com.oscar.proyecto.dto.InventarioIngrediente.InventarioIngredienteResponseDTO;
import com.oscar.proyecto.modelos.InventarioIngrediente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InventarioIngredienteMapper {

    @Mapping(target = "idInventario", source = "id.idInventario")
    @Mapping(target = "idIngrediente", source = "id.idIngrediente")
    @Mapping(target = "usuarioId", source = "inventario.usuario.id")
    @Mapping(target = "nombre", source = "ingrediente.nombre")
    @Mapping(target = "unidadMedida", source = "ingrediente.unidadMedida")
    @Mapping(target = "imagenUrl", source = "ingrediente.imagenUrl")
    @Mapping(target = "cantidad", source = "cantidad")
    InventarioIngredienteResponseDTO toResponseDTO(InventarioIngrediente inventarioIngrediente);

    List<InventarioIngredienteResponseDTO> toResponseDTOList(List<InventarioIngrediente> inventarioIngredientes);
}