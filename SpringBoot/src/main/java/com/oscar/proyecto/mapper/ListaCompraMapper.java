package com.oscar.proyecto.mapper;

import com.oscar.proyecto.dto.ListaCompra.IngredienteEnCarritoDTO;
import com.oscar.proyecto.dto.ListaCompra.ListaCompraResponseDTO;
import com.oscar.proyecto.dto.ListaCompra.ListaCompraIngredienteDTO;
import com.oscar.proyecto.modelos.ListaCompra;
import com.oscar.proyecto.modelos.ListaCompraIngrediente;
import com.oscar.proyecto.modelos.Ingrediente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface ListaCompraMapper {

    @Mapping(target = "idUsuario", source = "usuario.id")
    ListaCompraResponseDTO toListaCompraResponseDTO(ListaCompra listaCompra);

    List<ListaCompraResponseDTO> toListaCompraResponseDTOList(List<ListaCompra> listasCompra);

    IngredienteEnCarritoDTO toIngredienteEnCarritoDTO(Ingrediente ingrediente);

    @Mapping(target = "ingrediente", source = "ingrediente")
    @Mapping(target = "cantidad", source = "cantidad")
    ListaCompraIngredienteDTO toListaCompraIngredienteDTO(ListaCompraIngrediente listaIngrediente);

    List<ListaCompraIngredienteDTO> toListaCompraIngredienteDTOList(List<ListaCompraIngrediente> ingredientes);
}