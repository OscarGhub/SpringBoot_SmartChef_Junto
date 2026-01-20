package com.oscar.proyecto.servicios.Ver_Receta;

import com.oscar.proyecto.dto.Receta.RecetaResponseDTO;
import com.oscar.proyecto.mapper.RecetaMapper;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class VerRecetaServiceIntegrationTest {

    @InjectMocks
    private RecetaService recetaService;

    @Mock
    private RecetaRepository recetaRepo;

    @Mock
    private RecetaGuardadaRepository recetaGuardadaRepo;

    @Mock
    private RecetaMapper recetaMapper;

    @Test
    @DisplayName("Test Integrado Positivo -> Obtener información receta por ID")
    public void getRecetaByIdTest() {
        Integer idReceta = 1;

        Receta receta = new Receta();
        receta.setId(idReceta);
        receta.setTitulo("Lentejas con Chorizo");

        RecetaResponseDTO dtoSimulado = new RecetaResponseDTO();
        dtoSimulado.setTitulo("Lentejas con Chorizo");

        Mockito.when(recetaRepo.findById(idReceta)).thenReturn(Optional.of(receta));
        Mockito.when(recetaMapper.toResponseDTO(receta)).thenReturn(dtoSimulado);

        Mockito.when(recetaGuardadaRepo.contarGuardados(idReceta)).thenReturn(0);

        RecetaResponseDTO resultado = recetaService.getRecetaById(idReceta);

        assertNotNull(resultado);
        assertEquals("Lentejas con Chorizo", resultado.getTitulo());

        Mockito.verify(recetaRepo).findById(idReceta);
        Mockito.verify(recetaMapper).toResponseDTO(receta);
    }
}