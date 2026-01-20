package com.oscar.proyecto.servicios.Receta_Filtro;

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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class RecetaFiltroServiceIntegrationTest {

    @InjectMocks
    private RecetaService recetaService;

    @Mock
    private RecetaRepository recetaRepo;

    @Mock
    private RecetaGuardadaRepository recetaGuardadaRepo;

    @Mock
    private RecetaMapper recetaMapper;

    @Test
    @DisplayName("Test Integrado Positivo -> Filtrar las recetas por IDs de preferencias")
    public void testFiltrarPorPreferenciasEspecificas() {
        List<Integer> preferenciasIds = List.of(1, 2);

        Receta recetaFiltrada = new Receta();
        recetaFiltrada.setId(100);
        recetaFiltrada.setTitulo("Ensalada Proteica");

        RecetaResponseDTO dtoEsperado = new RecetaResponseDTO();
        dtoEsperado.setTitulo("Ensalada Proteica");

        Mockito.when(recetaRepo.findByPreferenciasIn(preferenciasIds))
                .thenReturn(List.of(recetaFiltrada));

        Mockito.when(recetaMapper.toResponseDTO(any(Receta.class)))
                .thenReturn(dtoEsperado);

        Mockito.when(recetaGuardadaRepo.contarGuardados(100))
                .thenReturn(3);

        List<RecetaResponseDTO> resultados = recetaService.filtrarRecetasPorPreferencias(preferenciasIds);

        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        assertEquals("Ensalada Proteica", resultados.get(0).getTitulo());
        assertEquals(3, resultados.get(0).getNumFavoritos());

        Mockito.verify(recetaRepo, Mockito.never()).findAll();
        Mockito.verify(recetaRepo).findByPreferenciasIn(preferenciasIds);
        Mockito.verify(recetaGuardadaRepo).contarGuardados(100);
    }
}