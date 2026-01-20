package com.oscar.proyecto.servicios.Historial_Semanal;

import com.oscar.proyecto.dto.Receta.RecetaUsoDTO;
import com.oscar.proyecto.mapper.RecetaMapper;
import com.oscar.proyecto.modelos.Receta;
import com.oscar.proyecto.modelos.RecetaCocinadaFecha;
import com.oscar.proyecto.modelos.Usuario;
import com.oscar.proyecto.repositorios.RecetaCocinadaFechaRepository;
import com.oscar.proyecto.servicios.RecetaCocinadaFechaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;

@ExtendWith(MockitoExtension.class)
public class RecetaCocinadaSemanalIntegrationTest {

    @InjectMocks
    private RecetaCocinadaFechaService service;

    @Mock
    private RecetaCocinadaFechaRepository cocinadaRepo;

    @Mock
    private RecetaMapper recetaMapper;

    @Test
    @DisplayName("Test de Integración -> Consultar historial semanal")
    public void testGetRecetasUltimaSemana() {
        Usuario u = new Usuario();
        u.setNombre("Oscar");

        Receta r1 = new Receta();
        r1.setTitulo("Pasta");

        RecetaCocinadaFecha hoy = new RecetaCocinadaFecha();
        hoy.setUsuario(u);
        hoy.setReceta(r1);
        hoy.setFechaCocinado(LocalDate.now());

        List<RecetaCocinadaFecha> listaSimulada = List.of(hoy);

        Mockito.doReturn(listaSimulada).when(cocinadaRepo).findRecetasUltimaSemana();

        RecetaUsoDTO dto = new RecetaUsoDTO();
        dto.setNombreReceta("Pasta");
        List<RecetaUsoDTO> listaDTOs = List.of(dto);

        Mockito.doReturn(listaDTOs).when(recetaMapper).toRecetaUsoDTOList(anyList());

        List<RecetaUsoDTO> resultados = service.getRecetasUltimaSemana();

        assertNotNull(resultados);
        assertEquals(1, resultados.size(), "La lista debería tener 1 elemento mapeado");
        assertEquals("Pasta", resultados.get(0).getNombreReceta());

        Mockito.verify(cocinadaRepo).findRecetasUltimaSemana();
        Mockito.verify(recetaMapper).toRecetaUsoDTOList(anyList());
    }
}