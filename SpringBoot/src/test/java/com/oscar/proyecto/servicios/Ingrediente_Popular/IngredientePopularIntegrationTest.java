package com.oscar.proyecto.servicios.Ingrediente_Popular;

import com.oscar.proyecto.dto.Ingrediente.TopIngredienteDTO;
import com.oscar.proyecto.mapper.IngredienteMapper;
import com.oscar.proyecto.modelos.IngredienteUsoProjection;
import com.oscar.proyecto.repositorios.IngredienteRepository;
import com.oscar.proyecto.servicios.IngredienteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;

@ExtendWith(MockitoExtension.class)
public class IngredientePopularIntegrationTest {

    @InjectMocks
    private IngredienteService service;

    @Mock
    private IngredienteRepository ingredienteRepository;

    @Mock
    private IngredienteMapper ingredienteMapper;

    @Test
    @DisplayName("Test de Integración -> Obtener Top 5 Ingredientes Exitoso")
    public void getTop5IngredientesIntegrationTest() {
        IngredienteUsoProjection projectionMock = Mockito.mock(IngredienteUsoProjection.class);
        List<IngredienteUsoProjection> proyeccionesSimuladas = Arrays.asList(projectionMock);

        TopIngredienteDTO dtoEsperado = new TopIngredienteDTO();
        List<TopIngredienteDTO> dtosEsperados = Arrays.asList(dtoEsperado);

        Mockito.when(ingredienteRepository.findTop5UsedIngredientsProjection())
                .thenReturn(proyeccionesSimuladas);

        Mockito.when(ingredienteMapper.toTopIngredienteDTOList(anyList()))
                .thenReturn(dtosEsperados);

        List<TopIngredienteDTO> resultado = service.getTop5Ingredientes();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());

        Mockito.verify(ingredienteRepository).findTop5UsedIngredientsProjection();
        Mockito.verify(ingredienteMapper).toTopIngredienteDTOList(proyeccionesSimuladas);
    }
}