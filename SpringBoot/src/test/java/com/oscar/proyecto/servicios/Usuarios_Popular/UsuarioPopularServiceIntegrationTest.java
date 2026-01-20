package com.oscar.proyecto.servicios.Usuarios_Popular;


import com.oscar.proyecto.dto.Receta.RecetaResponseDTO;
import com.oscar.proyecto.mapper.RecetaMapper;
import com.oscar.proyecto.modelos.Receta;
import com.oscar.proyecto.modelos.Usuario;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioPopularServiceIntegrationTest {

    @InjectMocks
    private RecetaService recetaService;

    @Mock
    private RecetaRepository recetaRepo;

    @Mock
    private RecetaGuardadaRepository recetaGuardadaRepo;

    @Mock
    private RecetaMapper recetaMapper;

    @Test
    @DisplayName("Test Integrado Positivo -> Obtener receta más guardada con sus usuarios")
    public void obtenerRecetaMasGuardadaConUsuariosTest() {
        Integer idRecetaMasGuardada = 50;

        Receta receta = new Receta();
        receta.setId(idRecetaMasGuardada);
        receta.setTitulo("Tarta de Queso");

        Usuario user1 = new Usuario();
        user1.setNombre("Oscar");
        Usuario user2 = new Usuario();
        user2.setNombre("Maria");
        List<Usuario> usuariosQueGuardaron = List.of(user1, user2);

        RecetaResponseDTO dtoBase = new RecetaResponseDTO();
        dtoBase.setTitulo("Tarta de Queso");

        Mockito.when(recetaGuardadaRepo.findRecetaMasGuardada()).thenReturn(idRecetaMasGuardada);

        Mockito.when(recetaRepo.findById(idRecetaMasGuardada)).thenReturn(Optional.of(receta));

        Mockito.when(recetaMapper.toResponseDTO(receta)).thenReturn(dtoBase);
        Mockito.when(recetaGuardadaRepo.contarGuardados(idRecetaMasGuardada)).thenReturn(2);

        Mockito.when(recetaGuardadaRepo.findUsuariosPorReceta(idRecetaMasGuardada)).thenReturn(usuariosQueGuardaron);

        RecetaResponseDTO resultado = recetaService.obtenerRecetaMasGuardadaConUsuarios();

        assertNotNull(resultado);
        assertEquals("Tarta de Queso", resultado.getTitulo());
        assertEquals(2, resultado.getNumFavoritos());

        assertNotNull(resultado.getUsuariosQueGuardaron());
        assertEquals(2, resultado.getUsuariosQueGuardaron().size());
        assertEquals("Oscar", resultado.getUsuariosQueGuardaron().get(0).getNombre());

        Mockito.verify(recetaGuardadaRepo).findRecetaMasGuardada();
        Mockito.verify(recetaRepo).findById(idRecetaMasGuardada);
        Mockito.verify(recetaGuardadaRepo).findUsuariosPorReceta(idRecetaMasGuardada);
    }
}