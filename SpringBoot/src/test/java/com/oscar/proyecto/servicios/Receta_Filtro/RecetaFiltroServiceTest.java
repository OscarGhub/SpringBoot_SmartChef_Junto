package com.oscar.proyecto.servicios.Receta_Filtro;

import com.oscar.proyecto.dto.Receta.RecetaRequestDTO;
import com.oscar.proyecto.dto.Receta.RecetaResponseDTO;
import com.oscar.proyecto.mapper.RecetaMapper;
import com.oscar.proyecto.modelos.Receta;
import com.oscar.proyecto.repositorios.RecetaGuardadaRepository;
import com.oscar.proyecto.repositorios.RecetaRepository;
import com.oscar.proyecto.repositorios.UsuarioRepository;
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
public class RecetaFiltroServiceTest {

    @Autowired
    private RecetaService recetaService;

    @MockitoBean
    private RecetaRepository recetaRepository;

    @MockitoBean
    private RecetaMapper recetaMapper;

    @MockitoBean
    private RecetaGuardadaRepository recetaGuardadaRepo;

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Test Unitario Positivo: Crear receta y verificar favoritos iniciales")
    public void testCrearRecetaUnitario() {
        RecetaRequestDTO request = new RecetaRequestDTO();
        request.setTitulo("Paella");

        Receta recetaEntidad = new Receta();
        recetaEntidad.setId(100);
        recetaEntidad.setTitulo("Paella");

        RecetaResponseDTO responseMapeado = new RecetaResponseDTO();
        responseMapeado.setId(100);
        responseMapeado.setTitulo("Paella");

        when(recetaMapper.toEntity(any())).thenReturn(recetaEntidad);
        when(recetaRepository.save(any())).thenReturn(recetaEntidad);
        when(recetaMapper.toResponseDTO(any())).thenReturn(responseMapeado);
        when(recetaGuardadaRepo.contarGuardados(100)).thenReturn(0);

        RecetaResponseDTO resultado = recetaService.crearReceta(request);

        assertNotNull(resultado);
        assertEquals(0, resultado.getNumFavoritos(), "Una receta nueva debe empezar con 0 favoritos");
        verify(recetaRepository, times(1)).save(any());
        verify(recetaGuardadaRepo, times(1)).contarGuardados(100);
    }

    @Test
    @DisplayName("Test Unitario Negativo: Eliminar receta inexistente")
    public void testEliminarRecetaInexistente() {
        when(recetaRepository.existsById(999)).thenReturn(false);

        assertDoesNotThrow(() -> recetaService.eliminarReceta(999));

        verify(recetaRepository, never()).deleteById(any());
    }
}