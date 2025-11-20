package com.oscar.proyecto.servicios;

import com.oscar.proyecto.dto.Ingrediente.IngredienteResponseDTO;
import com.oscar.proyecto.modelos.Ingrediente;
import com.oscar.proyecto.repositorios.IngredienteRepository;
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

    private IngredienteResponseDTO toResponse(Ingrediente ingrediente) {
        return new IngredienteResponseDTO(
                ingrediente.getId(),
                ingrediente.getNombre(),
                ingrediente.getUnidadMedida(),
                ingrediente.getImagen_url()
        );
    }
}
