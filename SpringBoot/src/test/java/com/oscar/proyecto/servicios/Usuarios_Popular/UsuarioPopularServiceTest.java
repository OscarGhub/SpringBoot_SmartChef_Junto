package com.oscar.proyecto.servicios.Usuarios_Popular;

import com.oscar.proyecto.dto.Receta.RecetaResponseDTO;
import com.oscar.proyecto.modelos.Receta;
import com.oscar.proyecto.modelos.RecetaGuardada;
import com.oscar.proyecto.modelos.RecetaGuardadaId;
import com.oscar.proyecto.modelos.Usuario;
import com.oscar.proyecto.repositorios.RecetaGuardadaRepository;
import com.oscar.proyecto.repositorios.RecetaRepository;
import com.oscar.proyecto.repositorios.UsuarioRepository;
import com.oscar.proyecto.servicios.RecetaService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioPopularServiceTest {

    @Autowired
    private RecetaService recetaService;

    @Autowired
    private RecetaRepository recetaRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private RecetaGuardadaRepository recetaGuardadaRepo;

    @BeforeAll
    void cargarDatos() {
        Receta receta = new Receta();
        receta.setTitulo("Tarta de Queso");
        receta = recetaRepo.save(receta);

        Usuario u1 = new Usuario();
        u1.setNombre("Oscar");
        u1.setCorreoElectronico("oscar@test.com");
        u1.setContrasena("123456");
        u1 = usuarioRepo.save(u1);

        Usuario u2 = new Usuario();
        u2.setNombre("Pepe");
        u2.setCorreoElectronico("pepe@test.com");
        u2.setContrasena("123456");
        u2 = usuarioRepo.save(u2);

        RecetaGuardada g1 = new RecetaGuardada();
        g1.setId(new RecetaGuardadaId(u1.getId(), receta.getId()));
        g1.setUsuario(u1);
        g1.setReceta(receta);
        recetaGuardadaRepo.save(g1);

        RecetaGuardada g2 = new RecetaGuardada();
        g2.setId(new RecetaGuardadaId(u2.getId(), receta.getId()));
        g2.setUsuario(u2);
        g2.setReceta(receta);
        recetaGuardadaRepo.save(g2);
    }

    @Test
    @DisplayName("Test Unitario -> Caso Positivo: Obtener receta más guardada")
    public void testObtenerRecetaMasGuardadaExitoso() {
        RecetaResponseDTO resultado = recetaService.obtenerRecetaMasGuardadaConUsuarios();

        assertNotNull(resultado, "El resultado no debería ser nula");
        assertEquals("Tarta de Queso", resultado.getTitulo());
        assertEquals(2, resultado.getNumFavoritos(), "Debería detectar los 2 guardados realizados en el BeforeAll");

        assertNotNull(resultado.getUsuariosQueGuardaron(), "La lista de usuarios que guardaron la receta debe venir rellena");

        boolean oscarPresente = resultado.getUsuariosQueGuardaron().stream()
                .anyMatch(u -> u.getNombre().equals("Oscar"));
        boolean pepePresente = resultado.getUsuariosQueGuardaron().stream()
                .anyMatch(u -> u.getNombre().equals("Pepe"));

        assertTrue(oscarPresente, "Oscar debería figurar en la lista de usuarios que guardaron la receta");
        assertTrue(pepePresente, "Pepe debería figurar en la lista de usuarios que guardaron la receta");
    }

    @Test
    @DisplayName("Test Unitario -> Caso Negativo: No hay datos")
    @Transactional
    public void testObtenerRecetaPopularVacio() {
        RecetaResponseDTO resultado = recetaService.obtenerRecetaMasGuardadaConUsuarios();
        assertNotNull(resultado, "En esta clase de test, el resultado no es nulo por los datos del BeforeAll");
    }
}
