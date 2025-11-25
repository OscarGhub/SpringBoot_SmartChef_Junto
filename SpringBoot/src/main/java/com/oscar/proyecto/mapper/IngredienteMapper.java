package com.oscar.proyecto.mapper;

import com.oscar.proyecto.dto.Ingrediente.IngredienteResponseDTO;
import com.oscar.proyecto.dto.Ingrediente.TopIngredienteDTO;
import com.oscar.proyecto.modelos.Ingrediente;
import com.oscar.proyecto.modelos.IngredienteUsoProjection;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IngredienteMapper {
    IngredienteResponseDTO toResponseDTO(Ingrediente ingrediente);
    List<IngredienteResponseDTO> toResponseDTOList(List<Ingrediente> ingredientes);

    TopIngredienteDTO toTopIngredienteDTO(IngredienteUsoProjection projection);
    List<TopIngredienteDTO> toTopIngredienteDTOList(List<IngredienteUsoProjection> projections);
}