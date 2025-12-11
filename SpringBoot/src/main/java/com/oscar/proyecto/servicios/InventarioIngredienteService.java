package com.oscar.proyecto.servicios;

import com.oscar.proyecto.dto.InventarioIngrediente.InventarioIngredienteRequestDTO;
import com.oscar.proyecto.dto.InventarioIngrediente.InventarioIngredienteResponseDTO;
import com.oscar.proyecto.exception.ElementoNoEncontradoException;
import com.oscar.proyecto.modelos.Inventario;
import com.oscar.proyecto.modelos.InventarioIngrediente;
import com.oscar.proyecto.modelos.InventarioIngredienteId;
import com.oscar.proyecto.modelos.Ingrediente;
import com.oscar.proyecto.repositorios.InventarioIngredienteRepository;
import com.oscar.proyecto.repositorios.InventarioRepository;
import com.oscar.proyecto.repositorios.IngredienteRepository;
import com.oscar.proyecto.mapper.InventarioIngredienteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventarioIngredienteService {

    private final InventarioIngredienteRepository inventarioIngredienteRepository;
    private final InventarioRepository inventarioRepository;
    private final IngredienteRepository ingredienteRepository;
    private final InventarioIngredienteMapper inventarioIngredienteMapper;

    public List<InventarioIngredienteResponseDTO> obtenerInventarioPorUsuario(Integer usuarioId) {
        return inventarioIngredienteMapper.toResponseDTOList(
                inventarioIngredienteRepository.findByInventarioUsuarioId(usuarioId)
        );
    }

    public InventarioIngredienteResponseDTO agregarIngredienteInventario(InventarioIngredienteRequestDTO request) {
        Inventario inventario = inventarioRepository.findById(request.getIdInventario())
                .orElseThrow(() -> new ElementoNoEncontradoException("Inventario no encontrado"));

        Ingrediente ingrediente = ingredienteRepository.findById(request.getIdIngrediente())
                .orElseThrow(() -> new ElementoNoEncontradoException("Ingrediente no encontrado"));

        InventarioIngredienteId id = new InventarioIngredienteId(inventario.getId(), ingrediente.getId());

        InventarioIngrediente inventarioIngrediente = new InventarioIngrediente();
        inventarioIngrediente.setId(id);
        inventarioIngrediente.setInventario(inventario);
        inventarioIngrediente.setIngrediente(ingrediente);
        inventarioIngrediente.setCantidad(request.getCantidad());

        InventarioIngrediente guardado = inventarioIngredienteRepository.save(inventarioIngrediente);

        return inventarioIngredienteMapper.toResponseDTO(guardado);
    }

    public void eliminarIngredienteInventario(Integer idInventario, Integer idIngrediente) {
        InventarioIngredienteId id = new InventarioIngredienteId(idInventario, idIngrediente);
        try {
            inventarioIngredienteRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ElementoNoEncontradoException("Ingrediente en Inventario no existe");
        }
    }

}