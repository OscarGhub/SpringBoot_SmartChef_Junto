package com.oscar.proyecto.servicios.Historial_Semanal;

import com.oscar.proyecto.dto.Receta.RecetaUsoDTO;
import com.oscar.proyecto.mapper.RecetaMapper;
import com.oscar.proyecto.modelos.RecetaUsoProjection;
import com.oscar.proyecto.repositorios.RecetaCocinadaFechaRepository;
import com.oscar.proyecto.servicios.RecetaCocinadaFechaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecetaCocinadaSemanalTest {

    @Mock
    private RecetaCocinadaFechaRepository cocinadaRepo;

    @Mock
    private RecetaMapper recetaMapper;

    @InjectMocks
    private RecetaCocinadaFechaService service;

    @Test
    @DisplayName("Test Unitario Positivo: Consultar historial semanal")
    public void testGetRecetasUltimaSemanaUnitario() {
        List<RecetaUsoProjection> listaSimulada = List.of(mock(RecetaUsoProjection.class));

        RecetaUsoDTO dtoSimulado = new RecetaUsoDTO();
        dtoSimulado.setNombreReceta("Pasta");
        dtoSimulado.setVecesCocinada(1L);

        when(cocinadaRepo.findRecetasUltimaSemana()).thenReturn(listaSimulada);
        when(recetaMapper.toRecetaUsoDTOList(listaSimulada)).thenReturn(List.of(dtoSimulado));

        List<RecetaUsoDTO> resultados = service.getRecetasUltimaSemana();

        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        assertEquals("Pasta", resultados.get(0).getNombreReceta());

        verify(cocinadaRepo).findRecetasUltimaSemana();
        verify(recetaMapper).toRecetaUsoDTOList(any());
    }

    @Test
    @DisplayName("Test Unitario Negativo: Lista vacía cuando no hay registros en la última semana")
    public void testGetRecetasUltimaSemanaVacia() {
        when(cocinadaRepo.findRecetasUltimaSemana()).thenReturn(List.of());
        when(recetaMapper.toRecetaUsoDTOList(anyList())).thenReturn(List.of());

        List<RecetaUsoDTO> resultados = service.getRecetasUltimaSemana();

        assertTrue(resultados.isEmpty());
        verify(cocinadaRepo, times(1)).findRecetasUltimaSemana();
    }
}