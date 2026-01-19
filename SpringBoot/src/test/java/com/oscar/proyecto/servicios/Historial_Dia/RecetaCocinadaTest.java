package com.oscar.proyecto.servicios.Historial_Dia;

import com.oscar.proyecto.dto.Receta.RecetaUsoDTO;
import com.oscar.proyecto.dto.Receta.RecetaUsoRequestDTO;
import com.oscar.proyecto.exception.ElementoNoEncontradoException;
import com.oscar.proyecto.mapper.RecetaMapper;
import com.oscar.proyecto.modelos.Receta;
import com.oscar.proyecto.modelos.RecetaCocinadaFecha;
import com.oscar.proyecto.modelos.Usuario;
import com.oscar.proyecto.repositorios.RecetaCocinadaFechaRepository;
import com.oscar.proyecto.repositorios.RecetaRepository;
import com.oscar.proyecto.repositorios.UsuarioRepository;
import com.oscar.proyecto.servicios.RecetaCocinadaFechaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecetaCocinadaTest {

    @Mock
    private RecetaCocinadaFechaRepository cocinadaRepo;

    @Mock
    private RecetaRepository recetaRepo;

    @Mock
    private UsuarioRepository usuarioRepo;

    @Mock
    private RecetaMapper recetaMapper;

    @InjectMocks
    private RecetaCocinadaFechaService service;

    private RecetaUsoRequestDTO recetaDTO;
    private Usuario usuario;
    private Receta receta;

    @BeforeEach
    void cargarDatos() {
        recetaDTO = new RecetaUsoRequestDTO();
        recetaDTO.setIdUsuario(1);
        recetaDTO.setIdReceta(10);
        recetaDTO.setFecha(LocalDate.now());

        usuario = new Usuario();
        usuario.setId(1);

        receta = new Receta();
        receta.setId(10);
        receta.setTitulo("Tortilla de Patatas");
    }

    @Test
    @DisplayName("Test Unitario Positivo: Guardar registro exitosamente")
    void guardarRecetaEnFecha_Exito() {
        when(recetaRepo.findById(10)).thenReturn(Optional.of(receta));
        when(usuarioRepo.findById(1)).thenReturn(Optional.of(usuario));
        when(cocinadaRepo.existsByUsuarioAndReceta(usuario, receta)).thenReturn(false);

        RecetaUsoDTO resultado = service.guardarRecetaEnFecha(recetaDTO);

        assertNotNull(resultado);
        assertEquals("Tortilla de Patatas", resultado.getNombreReceta());
        assertEquals(1L, resultado.getVecesCocinada());

        verify(cocinadaRepo, times(1)).save(any(RecetaCocinadaFecha.class));
    }

    @Test
    @DisplayName("Test Unitario Negativo: Lanzar excepción si la receta no existe")
    void guardarRecetaEnFecha_RecetaNoEncontrada() {
        when(recetaRepo.findById(10)).thenReturn(Optional.empty());

        assertThrows(ElementoNoEncontradoException.class, () -> {
            service.guardarRecetaEnFecha(recetaDTO);
        });

        verify(cocinadaRepo, never()).save(any());
    }
}