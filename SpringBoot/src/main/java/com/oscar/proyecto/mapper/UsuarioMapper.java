package com.oscar.proyecto.mapper;

import com.oscar.proyecto.dto.Usuario.UsuarioDTO;
import com.oscar.proyecto.modelos.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mappings({
            @Mapping(target = "contrasena", ignore = true),
            @Mapping(target = "confirmarContrasena", ignore = true)
    })
    UsuarioDTO toDTO(Usuario usuario);

    List<UsuarioDTO> toDTOList(List<Usuario> usuarios);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "contrasena", ignore = true),
            @Mapping(target = "fotoUrl", ignore = true),
            @Mapping(target = "inventario", ignore = true)
    })
    Usuario toEntity(UsuarioDTO dto);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "contrasena", ignore = true),
            @Mapping(target = "fotoUrl", ignore = true),
            @Mapping(target = "inventario", ignore = true)
    })
    void updateEntityFromDto(UsuarioDTO dto, @MappingTarget Usuario usuario);
}