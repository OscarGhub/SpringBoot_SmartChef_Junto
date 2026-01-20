package com.oscar.proyecto.servicios.Historial_Dia;

import com.oscar.proyecto.dto.Receta.RecetaUsoRequestDTO;
import com.oscar.proyecto.dto.Receta.RecetaUsoDTO;
import com.oscar.proyecto.mapper.RecetaMapper;
import com.oscar.proyecto.modelos.Receta;
import com.oscar.proyecto.modelos.Usuario;
import com.oscar.proyecto.modelos.RecetaCocinadaFecha;
import com.oscar.proyecto.repositorios.RecetaRepository;
import com.oscar.proyecto.repositorios.UsuarioRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class RecetaCocinadaIntegrationTest {

    @InjectMocks
    private RecetaCocinadaFechaService service;

    @Mock
    private UsuarioRepository usuarioRepo;

    @Mock
    private RecetaRepository recetaRepo;

    @Mock
    private RecetaCocinadaFechaRepository cocinadaRepo;

    @Mock
    private RecetaMapper recetaMapper;

    @Test
    @DisplayName("Test de Integración -> Guardar receta en fecha correctamente")
    public void testGuardarRecetaEnFechaExitoso() {
        Integer userId = 1;
        Integer recetaId = 10;

        RecetaUsoRequestDTO request = new RecetaUsoRequestDTO();
        request.setIdUsuario(userId);
        request.setIdReceta(recetaId);
        request.setFecha(LocalDate.now());

        Usuario usuarioSimulado = new Usuario();
        usuarioSimulado.setId(userId);

        Receta recetaSimulada = new Receta();
        recetaSimulada.setId(recetaId);
        recetaSimulada.setTitulo("Tortilla");

        RecetaUsoDTO dtoSimulado = new RecetaUsoDTO();
        dtoSimulado.setNombreReceta("Tortilla");

        Mockito.when(usuarioRepo.findById(userId)).thenReturn(Optional.of(usuarioSimulado));
        Mockito.when(recetaRepo.findById(recetaId)).thenReturn(Optional.of(recetaSimulada));

        Mockito.lenient().when(recetaMapper.toRecetaUsoDTO(any())).thenReturn(dtoSimulado);

        Mockito.lenient().when(cocinadaRepo.save(any(RecetaCocinadaFecha.class))).thenAnswer(i -> i.getArguments()[0]);

        RecetaUsoDTO response = service.guardarRecetaEnFecha(request);

        assertNotNull(response);
        assertEquals("Tortilla", response.getNombreReceta());

        Mockito.verify(usuarioRepo).findById(userId);
        Mockito.verify(recetaRepo).findById(recetaId);
    }
}