package com.oscar.proyecto.servicios.Receta_Favorita;

import com.oscar.proyecto.dto.Receta.RecetaResponseDTO;
import com.oscar.proyecto.mapper.RecetaMapper;
import com.oscar.proyecto.modelos.Receta;
import com.oscar.proyecto.modelos.RecetaGuardada;
import com.oscar.proyecto.modelos.RecetaGuardadaId;
import com.oscar.proyecto.modelos.Usuario;
import com.oscar.proyecto.repositorios.RecetaGuardadaRepository;
import com.oscar.proyecto.repositorios.RecetaRepository;
import com.oscar.proyecto.repositorios.UsuarioRepository;
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
public class RecetaFavoritaServiceIntegrationTest {

    @InjectMocks
    private RecetaService recetaService;

    @Mock
    private RecetaRepository recetaRepo;

    @Mock
    private RecetaGuardadaRepository recetaGuardadaRepo;

    @Mock
    private UsuarioRepository usuarioRepo;

    @Mock
    private RecetaMapper recetaMapper;

    @Test
    @DisplayName("Test de Integración Positivo -> Guardar Receta como Favorita")
    public void guardarRecetaFavoritaTest() {
        Integer idReceta = 1;
        Integer idUsuario = 10;

        Receta receta = new Receta();
        receta.setId(idReceta);
        receta.setTitulo("Paella");

        Usuario usuario = new Usuario();
        usuario.setId(idUsuario);

        RecetaResponseDTO responseDTO = new RecetaResponseDTO();
        responseDTO.setTitulo("Paella");

        Mockito.when(recetaRepo.findById(idReceta)).thenReturn(Optional.of(receta));
        Mockito.when(usuarioRepo.findById(idUsuario)).thenReturn(Optional.of(usuario));
        Mockito.when(recetaGuardadaRepo.existsById(any(RecetaGuardadaId.class))).thenReturn(false);
        Mockito.when(recetaMapper.toResponseDTO(receta)).thenReturn(responseDTO);
        Mockito.when(recetaGuardadaRepo.contarGuardados(idReceta)).thenReturn(5);

        RecetaResponseDTO resultado = recetaService.guardarReceta(idReceta, idUsuario);

        assertNotNull(resultado);
        assertTrue(resultado.getGuardada());
        assertEquals(5, resultado.getNumFavoritos());

        Mockito.verify(recetaGuardadaRepo).save(any(RecetaGuardada.class));
        Mockito.verify(recetaGuardadaRepo).contarGuardados(idReceta);
    }
}