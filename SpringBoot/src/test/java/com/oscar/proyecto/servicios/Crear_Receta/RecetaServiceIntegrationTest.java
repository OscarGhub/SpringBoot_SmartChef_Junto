package com.oscar.proyecto.servicios.Crear_Receta;

import com.oscar.proyecto.dto.Receta.RecetaRequestDTO;
import com.oscar.proyecto.dto.Receta.RecetaResponseDTO;
import com.oscar.proyecto.mapper.RecetaMapper; // Asumo que tienes un mapper
import com.oscar.proyecto.modelos.Receta;
import com.oscar.proyecto.repositorios.RecetaGuardadaRepository;
import com.oscar.proyecto.repositorios.RecetaRepository;
import com.oscar.proyecto.servicios.RecetaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class RecetaServiceIntegrationTest {

    @InjectMocks
    private RecetaService service;

    @Mock
    private RecetaRepository repository;

    @Mock
    private RecetaMapper mapper;

    @Mock
    private RecetaGuardadaRepository recetaGuardadaRepo;

    @Test
    @DisplayName("Test de Integración -> Agregar Receta con ingredientes y pasos")
    public void testIntegradoAgregarRecetaExitoso() {
        RecetaRequestDTO nuevaRecetaDTO = new RecetaRequestDTO();
        nuevaRecetaDTO.setTitulo("Tortilla de Patatas");
        nuevaRecetaDTO.setDescripcion("4 patatas, 6 huevos, sal");
        nuevaRecetaDTO.setTutorial("1. Freír patatas. 2. Batir huevos. 3. Mezclar y cuajar.");

        Receta recetaSimulada = new Receta();
        recetaSimulada.setId(1);
        recetaSimulada.setTitulo("Tortilla de Patatas");

        RecetaResponseDTO respuestaSimulada = new RecetaResponseDTO();
        respuestaSimulada.setId(1);
        respuestaSimulada.setTitulo("Tortilla de Patatas");

        Mockito.when(mapper.toEntity(any(RecetaRequestDTO.class))).thenReturn(recetaSimulada);
        Mockito.when(repository.save(any(Receta.class))).thenReturn(recetaSimulada);
        Mockito.when(mapper.toResponseDTO(any(Receta.class))).thenReturn(respuestaSimulada);

        service.crearReceta(nuevaRecetaDTO);

        Mockito.verify(repository).save(any(Receta.class));
        Mockito.verify(mapper).toEntity(any());
        Mockito.verify(mapper).toResponseDTO(any());
    }
}