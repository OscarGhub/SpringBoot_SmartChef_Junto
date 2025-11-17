package com.oscar.proyecto.service;

import com.oscar.proyecto.dto.Ingrediente.IngredienteRequestDTO;
import com.oscar.proyecto.dto.Ingrediente.IngredienteResponseDTO;
import com.oscar.proyecto.entity.Ingrediente;
import com.oscar.proyecto.repository.IngredienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IngredienteService {

    private final IngredienteRepository ingredienteRepository;

    public List<IngredienteResponseDTO> getAllIngredientes() {
        return ingredienteRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public IngredienteResponseDTO getIngredienteById(Integer id) {
        Ingrediente ingrediente = ingredienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingrediente no encontrado"));
        return toResponse(ingrediente);
    }

    public IngredienteResponseDTO crearIngrediente(IngredienteRequestDTO request) {
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setNombre(request.getNombre());
        ingrediente.setUnidadMedida(request.getUnidadMedida());
        ingrediente.setImagen_url(request.getImagenUrl());

        Ingrediente guardado = ingredienteRepository.save(ingrediente);
        return toResponse(guardado);
    }

    public IngredienteResponseDTO actualizarIngrediente(Integer id, IngredienteRequestDTO request) {
        Ingrediente ingrediente = ingredienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingrediente no encontrado"));

        ingrediente.setNombre(request.getNombre());
        ingrediente.setUnidadMedida(request.getUnidadMedida());
        ingrediente.setImagen_url(request.getImagenUrl());

        Ingrediente actualizado = ingredienteRepository.save(ingrediente);
        return toResponse(actualizado);
    }

    public void eliminarIngrediente(Integer id) {
        if (!ingredienteRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingrediente no existe");
        }
        ingredienteRepository.deleteById(id);
    }

    private IngredienteResponseDTO toResponse(Ingrediente ingrediente) {
        return new IngredienteResponseDTO(
                ingrediente.getId(),
                ingrediente.getNombre(),
                ingrediente.getUnidadMedida(),
                ingrediente.getImagen_url()
        );
    }
}
