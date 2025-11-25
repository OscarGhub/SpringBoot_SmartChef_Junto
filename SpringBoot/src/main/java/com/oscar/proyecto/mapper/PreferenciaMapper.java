package com.oscar.proyecto.mapper;

import com.oscar.proyecto.dto.Preferencia.PreferenciaDTO;
import com.oscar.proyecto.modelos.Preferencia;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PreferenciaMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    PreferenciaDTO toDTO(Preferencia preferencia);

    List<PreferenciaDTO> toDTOList(List<Preferencia> preferencias);
}