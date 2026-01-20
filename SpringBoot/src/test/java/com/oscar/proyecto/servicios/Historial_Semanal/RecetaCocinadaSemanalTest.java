//package com.oscar.proyecto.servicios.Historial_Semanal;
//
//import com.oscar.proyecto.dto.Receta.RecetaUsoDTO;
//import com.oscar.proyecto.modelos.Receta;
//import com.oscar.proyecto.modelos.RecetaCocinadaFecha;
//import com.oscar.proyecto.modelos.Usuario;
//import com.oscar.proyecto.servicios.RecetaCocinadaFechaService;
//import jakarta.persistence.EntityManager;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//public class RecetaCocinadaSemanalTest {
//
//    @Autowired
//    private RecetaCocinadaFechaService service;
//
//    @Autowired
//    private EntityManager entityManager;
//
//    @Test
//    @DisplayName("Servicio Historial Semanal -> Caso Positivo: Consultar recetas cocinadas")
//    public void testGetRecetasUltimaSemanaReal() {
//        Usuario u = new Usuario();
//        u.setNombre("Chef Oscar");
//        u.setCorreoElectronico("oscar@semana.com");
//        u.setContrasena("123");
//        entityManager.persist(u);
//
//        Receta r = new Receta();
//        r.setTitulo("Pasta");
//        entityManager.persist(r);
//
//        RecetaCocinadaFecha registroHoy = new RecetaCocinadaFecha();
//        registroHoy.setUsuario(u);
//        registroHoy.setReceta(r);
//        registroHoy.setFechaCocinado(LocalDate.now());
//        entityManager.persist(registroHoy);
//
//        entityManager.flush();
//
//        List<RecetaUsoDTO> resultados = service.getRecetasUltimaSemana();
//
//        assertNotNull(resultados);
//        assertFalse(resultados.isEmpty(), "Debería haber al menos un registro de esta semana");
//
//        RecetaUsoDTO pasta = resultados.stream()
//                .filter(dto -> dto.getNombreReceta().equals("Pasta"))
//                .findFirst()
//                .orElseThrow();
//
//        assertEquals(1L, pasta.getVecesCocinada());
//    }
//
//    @Test
//    @DisplayName("Servicio Historial Semanal -> Caso Negativo: Registros fuera de rango")
//    public void testGetRecetasUltimaSemanaFueraDeRango() {
//        Usuario u = new Usuario();
//        u.setNombre("User Test");
//        u.setCorreoElectronico("test@fuera.com");
//        u.setContrasena("123");
//        entityManager.persist(u);
//
//        Receta r = new Receta();
//        r.setTitulo("Receta Antigua");
//        entityManager.persist(r);
//
//        RecetaCocinadaFecha registroAntiguo = new RecetaCocinadaFecha();
//        registroAntiguo.setUsuario(u);
//        registroAntiguo.setReceta(r);
//        registroAntiguo.setFechaCocinado(LocalDate.now().minusDays(15));
//        entityManager.persist(registroAntiguo);
//
//        entityManager.flush();
//
//        List<RecetaUsoDTO> resultados = service.getRecetasUltimaSemana();
//
//        assertTrue(resultados.stream().noneMatch(dto -> dto.getNombreReceta().equals("Receta Antigua")),
//                "No debería mostrar recetas cocinadas hace más de 7 días");
//    }
//}