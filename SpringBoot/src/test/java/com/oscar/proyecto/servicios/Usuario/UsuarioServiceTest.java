package com.oscar.proyecto.servicios.Usuario;

import com.oscar.proyecto.dto.Usuario.UsuarioDTO;
import com.oscar.proyecto.exception.ElementoNoEncontradoException;
import com.oscar.proyecto.modelos.Usuario;
import com.oscar.proyecto.repositorios.UsuarioRepository;
import com.oscar.proyecto.servicios.UsuarioService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService service;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Test
    @DisplayName("Servicio Usuario -> Caso Positivo: Registro")
    public void testRegistroUsuarioExitoso() {
        UsuarioDTO nuevoUsuarioDTO = new UsuarioDTO();
        nuevoUsuarioDTO.setNombre("Oscar Nuevo");
        nuevoUsuarioDTO.setCorreoElectronico("oscar@nuevo.com");
        nuevoUsuarioDTO.setContrasena("123456");
        nuevoUsuarioDTO.setConfirmarContrasena("123456");

        service.crearUsuario(nuevoUsuarioDTO);

        Optional<Usuario> usuarioEnBD = usuarioRepo.findAll().stream()
                .filter(u -> u.getCorreoElectronico().equals("oscar@nuevo.com"))
                .findFirst();

        assertTrue(usuarioEnBD.isPresent(), "El usuario debería existir en la BD");
        assertEquals("Oscar Nuevo", usuarioEnBD.get().getNombre());
    }

    @Test
    @DisplayName("Servicio Usuario -> Caso Negativo: Contraseñas no coinciden")
    public void testContrasenasNoCoinciden() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombre("Oscar");
        dto.setCorreoElectronico("error@test.com");
        dto.setContrasena("123456");
        dto.setConfirmarContrasena("999999");

        assertThrows(ElementoNoEncontradoException.class, () -> {
            service.crearUsuario(dto);
        }, "El servicio debería lanzar excepción si las contraseñas no coinciden");
    }
}
