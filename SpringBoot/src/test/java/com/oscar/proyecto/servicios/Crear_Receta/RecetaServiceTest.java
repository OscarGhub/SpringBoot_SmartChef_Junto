//package com.oscar.proyecto.servicios.Crear_Receta;
//
//import com.oscar.proyecto.dto.Receta.RecetaRequestDTO;
//import com.oscar.proyecto.dto.Receta.RecetaResponseDTO;
//import com.oscar.proyecto.modelos.Receta;
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
//public class RecetaServiceTest {
//
//    @Autowired
//    private RecetaService recetaService;
//
//    @Autowired
//    private EntityManager entityManager;
//
//    @Test
//    @DisplayName("Servicio Receta -> Caso Positivo: Crear receta")
//    public void testCrearRecetaExitoso() {
//        RecetaRequestDTO request = new RecetaRequestDTO();
//        request.setTitulo("Paella Valenciana");
//
//        RecetaResponseDTO resultado = recetaService.crearReceta(request);
//
//        assertNotNull(resultado);
//        assertNotNull(resultado.getId());
//
//        Receta recetaEnBD = entityManager.createQuery(
//                        "SELECT r FROM Receta r WHERE r.titulo = :titulo", Receta.class)
//                .setParameter("titulo", "Paella Valenciana")
//                .getSingleResult();
//
//        assertNotNull(recetaEnBD, "La receta debería existir en la base de datos");
//        assertEquals("Paella Valenciana", recetaEnBD.getTitulo());
//    }
//
//    @Test
//    @DisplayName("Servicio Receta -> Caso Negativo: Crear receta sin título")
//    public void testCrearRecetaSinTitulo() {
//        RecetaRequestDTO recetaInvalida = new RecetaRequestDTO();
//        recetaInvalida.setTitulo(null);
//
//        assertThrows(org.springframework.dao.DataIntegrityViolationException.class, () -> {
//            recetaService.crearReceta(recetaInvalida);
//            entityManager.flush();
//        }, "Debería lanzar una excepción de integridad si el título es nulo");
//    }
//}