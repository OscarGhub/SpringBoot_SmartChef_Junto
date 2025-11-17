package com.oscar.proyecto.service;

import com.oscar.proyecto.dto.InventarioIngrediente.InventarioIngredienteRequestDTO;
import com.oscar.proyecto.dto.InventarioIngrediente.InventarioIngredienteResponseDTO;
import com.oscar.proyecto.entity.Inventario;
import com.oscar.proyecto.entity.InventarioIngrediente;
import com.oscar.proyecto.entity.InventarioIngredienteId;
import com.oscar.proyecto.entity.Ingrediente;
import com.oscar.proyecto.repository.InventarioIngredienteRepository;
import com.oscar.proyecto.repository.InventarioRepository;
import com.oscar.proyecto.repository.IngredienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventarioIngredienteService {

    private final InventarioIngredienteRepository inventarioIngredienteRepository;
    private final InventarioRepository inventarioRepository;
    private final IngredienteRepository ingredienteRepository;

    public List<InventarioIngredienteResponseDTO> obtenerInventarioPorUsuario(Integer usuarioId) {
        return inventarioIngredienteRepository.findByInventarioUsuarioId(usuarioId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public InventarioIngredienteResponseDTO agregarIngrediente(InventarioIngredienteRequestDTO request) {
        Inventario inventario = inventarioRepository.findById(request.getIdInventario())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventario no encontrado"));

        Ingrediente ingrediente = ingredienteRepository.findById(request.getIdIngrediente())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingrediente no encontrado"));

        InventarioIngredienteId id = new InventarioIngredienteId(inventario.getId(), ingrediente.getId());

        InventarioIngrediente inventarioIngrediente = new InventarioIngrediente();
        inventarioIngrediente.setId(id);
        inventarioIngrediente.setInventario(inventario);
        inventarioIngrediente.setIngrediente(ingrediente);
        inventarioIngrediente.setCantidad(request.getCantidad());

        InventarioIngrediente guardado = inventarioIngredienteRepository.save(inventarioIngrediente);
        return toResponseDTO(guardado);
    }

    public void eliminarIngrediente(Integer idInventario, Integer idIngrediente) {
        InventarioIngredienteId id = new InventarioIngredienteId(idInventario, idIngrediente);
        try {
            inventarioIngredienteRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingrediente en inventario no existe");
        }
    }

    private InventarioIngredienteResponseDTO toResponseDTO(InventarioIngrediente i) {
        return new InventarioIngredienteResponseDTO(
                i.getId().getIdInventario(),
                i.getId().getIdIngrediente(),
                i.getInventario().getUsuario().getId(),
                i.getIngrediente().getNombre(),
                i.getIngrediente().getUnidadMedida(),
                i.getIngrediente().getImagen_url(),
                i.getCantidad()
        );
    }
}
