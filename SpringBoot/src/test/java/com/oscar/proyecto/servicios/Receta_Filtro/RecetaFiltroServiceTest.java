//import com.oscar.proyecto.dto.Receta.RecetaResponseDTO;
//import com.oscar.proyecto.modelos.Receta;
//import com.oscar.proyecto.modelos.RecetaGuardada;
//import com.oscar.proyecto.modelos.RecetaGuardadaId;
//import com.oscar.proyecto.modelos.Usuario;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//
//@Test
//@DisplayName("Test Integración: Buscar recetas con filtros y verificar favoritos reales")
//public void testFiltrarPorPreferencias() {
//    Receta receta = new Receta();
//    receta.setTitulo("Pasta Carbonara");
//    receta = recetaRepo.save(receta);
//
//    Usuario user = new Usuario();
//    user.setNombre("Oscar");
//    user.setCorreoElectronico("oscar@test.com");
//    user.setContrasena("123456");
//    user = usuarioRepo.save(user);
//
//    RecetaGuardadaId rgId = new RecetaGuardadaId(user.getId(), receta.getId());
//    RecetaGuardada rg = new RecetaGuardada();
//    rg.setId(rgId);
//    rg.setUsuario(user);
//    rg.setReceta(receta);
//    recetaGuardadaRepo.save(rg);
//
//    recetaGuardadaRepo.flush();
//
//    List<RecetaResponseDTO> resultados = recetaService.filtrarRecetasPorPreferencias(null);
//
//    assertFalse(resultados.isEmpty(), "La lista no debería estar vacía");
//
//    RecetaResponseDTO dto = resultados.stream()
//            .filter(r -> "Pasta Carbonara".equals(r.getTitulo()))
//            .findFirst()
//            .orElseThrow(() -> new AssertionError("No se encontró la receta guardada"));
//
//    assertEquals(1, dto.getNumFavoritos(), "El contador real de la BD debería ser 1");
//}