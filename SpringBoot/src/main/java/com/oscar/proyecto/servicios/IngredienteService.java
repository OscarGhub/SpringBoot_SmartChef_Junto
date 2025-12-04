package com.oscar.proyecto.servicios;

import com.oscar.proyecto.dto.Ingrediente.IngredienteResponseDTO;
import com.oscar.proyecto.dto.Ingrediente.TopIngredienteDTO;
import com.oscar.proyecto.exception.ElementoNoEncontradoException;
import com.oscar.proyecto.mapper.IngredienteMapper;
import com.oscar.proyecto.modelos.Ingrediente;
import com.oscar.proyecto.modelos.IngredienteUsoProjection;
import com.oscar.proyecto.repositorios.IngredienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredienteService {

    private final IngredienteRepository ingredienteRepository;
    private final IngredienteMapper ingredienteMapper;

    public List<IngredienteResponseDTO> getAllIngredientes() {
        List<Ingrediente> ingredientes = ingredienteRepository.findAll();
        return ingredienteMapper.toResponseDTOList(ingredientes);
    }

    public IngredienteResponseDTO getIngredienteById(Integer id) {
        Ingrediente ingrediente = ingredienteRepository.findById(id)
                .orElseThrow(() -> new ElementoNoEncontradoException("Ingrediente no encontrado"));
        return ingredienteMapper.toResponseDTO(ingrediente);
    }

    public List<TopIngredienteDTO> getTop5Ingredientes() {
        List<IngredienteUsoProjection> proyecciones = ingredienteRepository.findTop5UsedIngredientsProjection();

        return ingredienteMapper.toTopIngredienteDTOList(proyecciones);
    }
}