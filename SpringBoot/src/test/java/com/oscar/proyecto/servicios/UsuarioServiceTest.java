package com.oscar.proyecto.servicios;

import com.oscar.proyecto.modelos.Usuario;
import com.oscar.proyecto.repositorios.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void cargarDatos() {
        usuarioRepository.deleteAll();
        Usuario usuario = new Usuario();
        usuario.setNombre("Oscar");
        usuario.setCorreoElectronico("oscar@ejemplo.com");
        usuarioRepository.save(usuario);
    }

    @Test
    @DisplayName("Negativo: Registro Usuario - El email ya existe")
    public void testRegistroUsuarioEmailDuplicado() {
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre("Otro Usuario");
        nuevoUsuario.setCorreoElectronico("oscar@ejemplo.com");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            usuarioService.crearUsuario(nuevoUsuario);
        });

        assertTrue(exception.getMessage().contains("email ya está registrado"),
                "El mensaje de error debería indicar que el email ya existe");
    }
}
