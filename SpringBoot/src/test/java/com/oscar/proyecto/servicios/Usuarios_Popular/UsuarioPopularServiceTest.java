//package com.oscar.proyecto.servicios.Usuarios_Popular;
//
//import com.oscar.proyecto.dto.Receta.RecetaResponseDTO;
//import com.oscar.proyecto.modelos.Receta;
//import com.oscar.proyecto.modelos.RecetaGuardada;
//import com.oscar.proyecto.modelos.RecetaGuardadaId;
//import com.oscar.proyecto.modelos.Usuario;
//import com.oscar.proyecto.servicios.RecetaService;
//import jakarta.persistence.EntityManager;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//public class UsuarioPopularServiceTest {
//
//    @Autowired
//    private RecetaService recetaService;
//
//    @Autowired
//    private EntityManager entityManager;
//
//    @Test
//    @DisplayName("Test Unitario -> Caso Positivo: Obtener receta con más guardados")
//    public void testObtenerRecetaMasGuardadaExitoso() {
//        Receta receta = new Receta();
//        receta.setTitulo("Tarta de Queso");
//        entityManager.persist(receta);
//
//        Usuario u1 = new Usuario();
//        u1.setNombre("Oscar");
//        u1.setCorreoElectronico("oscar@test.com");
//        u1.setContrasena("123");
//        entityManager.persist(u1);
//
//        Usuario u2 = new Usuario();
//        u2.setNombre("Maria");
//        u2.setCorreoElectronico("maria@test.com");
//        u2.setContrasena("123");
//        entityManager.persist(u2);
//
//        entityManager.flush();
//
//        RecetaGuardada g1 = new RecetaGuardada();
//        g1.setId(new RecetaGuardadaId(u1.getId(), receta.getId()));
//        g1.setUsuario(u1);
//        g1.setReceta(receta);
//        entityManager.persist(g1);
//
//        RecetaGuardada g2 = new RecetaGuardada();
//        g2.setId(new RecetaGuardadaId(u2.getId(), receta.getId()));
//        g2.setUsuario(u2);
//        g2.setReceta(receta);
//        entityManager.persist(g2);
//
//        entityManager.flush();
//        entityManager.clear();
//
//        RecetaResponseDTO resultado = recetaService.obtenerRecetaMasGuardadaConUsuarios();
//
//        assertNotNull(resultado, "El resultado no debería ser nulo");
//        assertEquals("Tarta de Queso", resultado.getTitulo());
//        assertEquals(2, resultado.getNumFavoritos());
//
//        assertNotNull(resultado.getUsuariosQueGuardaron());
//        assertTrue(resultado.getUsuariosQueGuardaron().stream()
//                .anyMatch(u -> u.getNombre().equals("Oscar")), "Oscar debería estar en la lista");
//    }
//
//    @Test
//    @DisplayName("Test Unitario -> Caso Negativo: No hay recetas guardadas")
//    public void testObtenerRecetaPopularVacio() {
//        RecetaResponseDTO resultado = recetaService.obtenerRecetaMasGuardadaConUsuarios();
//
//        assertNull(resultado, "Debería devolver null si no hay ninguna receta guardada por nadie");
//    }
//}