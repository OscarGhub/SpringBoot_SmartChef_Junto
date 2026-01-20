package com.oscar.proyecto.servicios.Usuario;

import com.oscar.proyecto.dto.Usuario.UsuarioDTO;
import com.oscar.proyecto.exception.ElementoNoEncontradoException;
import com.oscar.proyecto.modelos.Usuario;
import com.oscar.proyecto.servicios.UsuarioService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeAll;
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
    private UsuarioService service;

    @Autowired
    private EntityManager entityManager;

    @BeforeAll
    static void cargarDatos() {
        Usuario u = new Usuario();
        u.setNombre("Chef Existente");
        u.setCorreoElectronico("existente@test.com");
        u.setContrasena("hash_password_123");
    }

    @Test
    @DisplayName("Servicio Usuario -> Caso Positivo: Registro")
    public void testRegistroUsuarioExitoso() {
        UsuarioDTO nuevoUsuarioDTO = new UsuarioDTO();
        nuevoUsuarioDTO.setNombre("Oscar Nuevo");
        nuevoUsuarioDTO.setCorreoElectronico("oscar@nuevo.com");
        nuevoUsuarioDTO.setContrasena("123456");
        nuevoUsuarioDTO.setConfirmarContrasena("123456");

        service.crearUsuario(nuevoUsuarioDTO);

        Usuario usuarioEnBD = entityManager.createQuery(
                        "SELECT u FROM Usuario u WHERE u.correoElectronico = :email", Usuario.class)
                .setParameter("email", "oscar@nuevo.com")
                .getSingleResult();

        assertNotNull(usuarioEnBD, "El usuario debería existir en la BD");
        assertEquals("Oscar Nuevo", usuarioEnBD.getNombre());
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