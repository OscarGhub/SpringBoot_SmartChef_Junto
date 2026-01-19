package com.oscar.proyecto.servicios.Crear_Receta;

import com.oscar.proyecto.dto.Receta.RecetaRequestDTO;
import com.oscar.proyecto.dto.Receta.RecetaResponseDTO;
import com.oscar.proyecto.mapper.RecetaMapper;
import com.oscar.proyecto.modelos.Receta;
import com.oscar.proyecto.repositorios.RecetaGuardadaRepository;
import com.oscar.proyecto.repositorios.RecetaRepository;
import com.oscar.proyecto.servicios.RecetaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RecetaServiceTest {

    @Autowired
    private RecetaService recetaService;

    @MockitoBean
    private RecetaRepository recetaRepository;

    @MockitoBean
    private RecetaMapper recetaMapper;

    @MockitoBean
    private RecetaGuardadaRepository recetaGuardadaRepo;

    @Test
    @DisplayName("Test Unitario Positivo: Crear receta")
    public void testCrearReceta() {
        RecetaRequestDTO request = new RecetaRequestDTO();
        request.setTitulo("Paella");

        Receta recetaSimulada = new Receta();
        recetaSimulada.setId(100);
        recetaSimulada.setTitulo("Paella");

        RecetaResponseDTO responseSimulado = new RecetaResponseDTO();
        responseSimulado.setId(100);
        responseSimulado.setTitulo("Paella");

        when(recetaMapper.toEntity(any(RecetaRequestDTO.class))).thenReturn(recetaSimulada);
        when(recetaRepository.save(any(Receta.class))).thenReturn(recetaSimulada);
        when(recetaMapper.toResponseDTO(any(Receta.class))).thenReturn(responseSimulado);
        when(recetaGuardadaRepo.contarGuardados(anyInt())).thenReturn(0);

        RecetaResponseDTO resultado = recetaService.crearReceta(request);

        assertNotNull(resultado);
        assertEquals(100, resultado.getId());
        verify(recetaRepository, times(1)).save(any(Receta.class));
    }

    @Test
    @DisplayName("Test Unitario Negativo: Agregar Receta - Faltan pasos")
    public void testAgregarRecetaFaltanPasos() {
        RecetaRequestDTO recetaSinPasos = new RecetaRequestDTO();
        recetaSinPasos.setTitulo("Receta incompleta");
        recetaSinPasos.setTutorial(null);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            recetaService.crearReceta(recetaSinPasos);
        });

        assertTrue(exception.getMessage().toLowerCase().contains("pasos") ||
                        exception.getMessage().toLowerCase().contains("tutorial"),
                "El mensaje real fue: " + exception.getMessage());
    }
}