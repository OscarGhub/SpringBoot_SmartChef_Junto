package com.oscar.proyecto.servicios.Usuario;

import com.oscar.proyecto.dto.Usuario.UsuarioDTO;
import com.oscar.proyecto.mapper.UsuarioMapper;
import com.oscar.proyecto.modelos.Usuario;
import com.oscar.proyecto.repositorios.UsuarioRepository;
import com.oscar.proyecto.servicios.InventarioService;
import com.oscar.proyecto.servicios.UsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceIntegrationTest {

    @InjectMocks
    private UsuarioService service;

    @Mock
    private UsuarioRepository repository;

    @Mock
    private UsuarioMapper mapper;

    @Mock
    private InventarioService inventarioService;

    @Test
    @DisplayName("Test de Integración -> Crear Usuario Exitoso")
    public void crearUsuarioIntegrationTest() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombre("Oscar");
        dto.setCorreoElectronico("oscar@test.com");
        dto.setContrasena("123");
        dto.setConfirmarContrasena("123");

        Usuario usuarioSimulado = new Usuario();
        usuarioSimulado.setNombre("Oscar");
        usuarioSimulado.setCorreoElectronico("oscar@test.com");

        Mockito.when(mapper.toEntity(any(UsuarioDTO.class))).thenReturn(usuarioSimulado);
        Mockito.when(repository.save(any(Usuario.class))).thenReturn(usuarioSimulado);

        service.crearUsuario(dto);

        Mockito.verify(repository).save(any(Usuario.class));
        Mockito.verify(mapper).toEntity(any());
    }
}
