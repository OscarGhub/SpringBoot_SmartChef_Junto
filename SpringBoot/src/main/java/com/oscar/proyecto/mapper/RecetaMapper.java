package com.oscar.proyecto.mapper;

import com.oscar.proyecto.dto.Receta.RecetaRequestDTO;
import com.oscar.proyecto.dto.Receta.RecetaResponseDTO;
import com.oscar.proyecto.dto.Receta.RecetaUsoDTO;
import com.oscar.proyecto.modelos.Receta;
import com.oscar.proyecto.modelos.RecetaUsoProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RecetaMapper {

    @Mapping(target = "nombreReceta", source = "nombreReceta")
    @Mapping(target = "vecesCocinada", source = "vecesCocinada")
    RecetaUsoDTO toRecetaUsoDTO(RecetaUsoProjection projection);

    List<RecetaUsoDTO> toRecetaUsoDTOList(List<RecetaUsoProjection> proyecciones);

    @Mapping(target = "guardada", ignore = true)
    RecetaResponseDTO toResponseDTO(Receta receta);

    List<RecetaResponseDTO> toResponseDTOList(List<Receta> recetas);

    Receta toEntity(RecetaRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(RecetaRequestDTO dto, @MappingTarget Receta receta);
}