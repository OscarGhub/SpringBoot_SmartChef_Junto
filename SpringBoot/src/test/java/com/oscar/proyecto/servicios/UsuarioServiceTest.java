package com.oscar.proyecto.servicios;

import com.oscar.proyecto.dto.Usuario.UsuarioDTO;
import com.oscar.proyecto.modelos.Usuario;
import com.oscar.proyecto.repositorios.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    void cargarDatos() {
        usuarioRepository.deleteAll();

        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setNombre("Oscar");
        usuarioExistente.setCorreoElectronico("oscar@ejemplo.com");
        usuarioExistente.setContrasena("123456");

        usuarioRepository.save(usuarioExistente);
    }

    @Test
    @DisplayName("Negativo: Registro Usuario - El email ya existe")
    public void testRegistroUsuarioEmailDuplicado() {
        UsuarioDTO dtoRepetido = new UsuarioDTO();
        dtoRepetido.setNombre("Intento");
        dtoRepetido.setCorreoElectronico("oscar@ejemplo.com");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            usuarioService.crearUsuario(dtoRepetido);
        });

        assertTrue(exception.getMessage().contains("el email ya está registrado"));
    }

    @Test
    @DisplayName("Negativo: Registro Usuario - Faltan datos obligatorios")
    public void testRegistroUsuarioDatosFaltantes() {
        UsuarioDTO dtoIncompleto = new UsuarioDTO();

        Exception exception = assertThrows(RuntimeException.class, () -> {
            usuarioService.crearUsuario(dtoIncompleto);
        });

        assertTrue(exception.getMessage().contains("Faltan datos obligatorios"));
    }

    @Test
    @DisplayName("Positivo: Registrar usuario con preferencias alimentarias")
    public void testRegistroUsuarioExitoso() {
        UsuarioDTO nuevoUsuarioDTO = new UsuarioDTO();
        nuevoUsuarioDTO.setNombre("Oscar Nuevo");
        nuevoUsuarioDTO.setCorreoElectronico("oscar@nuevo.com");

        nuevoUsuarioDTO.setContrasena("password123");
        nuevoUsuarioDTO.setConfirmarContrasena("password123");


        Usuario guardado = usuarioService.crearUsuario(nuevoUsuarioDTO);

        assertNotNull(guardado, "El usuario guardado no debería ser nulo");
        assertEquals("oscar@nuevo.com", guardado.getCorreoElectronico(), "El email debe coincidir");

        assertTrue(usuarioRepository.findByCorreoElectronico("oscar@nuevo.com").isPresent());
    }

}