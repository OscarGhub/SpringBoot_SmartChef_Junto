package com.oscar.proyecto.servicios.Usuario;

import com.oscar.proyecto.dto.Usuario.UsuarioDTO;
import com.oscar.proyecto.exception.ElementoNoEncontradoException;
import com.oscar.proyecto.repositorios.UsuarioRepository;
import com.oscar.proyecto.servicios.UsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Test Negativo: Error cuando las contraseñas no coinciden")
    public void testContrasenasNoCoinciden() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombre("Oscar");
        dto.setCorreoElectronico("oscar@test.com");
        dto.setContrasena("123456");
        dto.setConfirmarContrasena("999999");

        Exception exception = assertThrows(ElementoNoEncontradoException.class, () -> {
            usuarioService.crearUsuario(dto);
        });

        assertTrue(exception.getMessage().contains("coinciden"),
                "El mensaje debería indicar que las contraseñas no coinciden");
    }

    @Test
    @DisplayName("Test Positivo: Registrar Usuario")
    public void testRegistroUsuarioExitoso() {
        UsuarioDTO nuevoUsuarioDTO = new UsuarioDTO();
        nuevoUsuarioDTO.setNombre("Oscar Nuevo");
        nuevoUsuarioDTO.setCorreoElectronico("oscar@nuevo.com");
        nuevoUsuarioDTO.setContrasena("123456");
        nuevoUsuarioDTO.setConfirmarContrasena("123456");

        usuarioService.crearUsuario(nuevoUsuarioDTO);

        var usuarioEnBD = usuarioRepository.findByCorreoElectronico("oscar@nuevo.com");

        assertTrue(usuarioEnBD.isPresent(), "El usuario debería existir en la BD");
        assertEquals("Oscar Nuevo", usuarioEnBD.get().getNombre());

    }
}