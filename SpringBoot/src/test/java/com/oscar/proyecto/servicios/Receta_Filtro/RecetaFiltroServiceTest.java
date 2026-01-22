package com.oscar.proyecto.servicios.Receta_Filtro;

import com.oscar.proyecto.dto.Receta.RecetaResponseDTO;
import com.oscar.proyecto.modelos.Receta;
import com.oscar.proyecto.modelos.RecetaGuardada;
import com.oscar.proyecto.modelos.RecetaGuardadaId;
import com.oscar.proyecto.modelos.Usuario;
import com.oscar.proyecto.servicios.RecetaService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class RecetaFiltroServiceTest {

    @Autowired
    private RecetaService recetaService;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Test Unitario -> Caso Positivo: Filtrar recetas por preferencias (Lectura con filtros)")
    public void testFiltrarPorPreferenciasExitoso() {
        Receta receta = new Receta();
        receta.setTitulo("Pasta Carbonara");
        entityManager.persist(receta);

        Usuario u = new Usuario();
        u.setNombre("Oscar");
        u.setCorreoElectronico("oscar@filtro.com");
        u.setContrasena("123");
        entityManager.persist(u);

        entityManager.flush();

        RecetaGuardada rg = new RecetaGuardada();
        rg.setId(new RecetaGuardadaId(u.getId(), receta.getId()));
        rg.setUsuario(u);
        rg.setReceta(receta);
        entityManager.persist(rg);

        entityManager.flush();
        entityManager.clear();

        List<RecetaResponseDTO> resultados = recetaService.filtrarRecetasPorPreferencias(null);

        assertFalse(resultados.isEmpty());
        RecetaResponseDTO dto = resultados.stream()
                .filter(r -> r.getTitulo().equals("Pasta Carbonara"))
                .findFirst()
                .orElse(null);

        assertNotNull(dto);
        assertEquals(1, dto.getNumFavoritos(), "El contador de favoritos debería ser 1 basado en los datos de la BD");
    }

    @Test
    @DisplayName("Test Unitario -> Caso Negativo: Filtrar con preferencias inexistentes")
    public void testFiltrarPorPreferenciasVacio() {
        Receta receta = new Receta();
        receta.setTitulo("Ensalada");
        entityManager.persist(receta);
        entityManager.flush();

        List<Integer> preferenciasNoValidas = List.of(999);
        List<RecetaResponseDTO> resultados = recetaService.filtrarRecetasPorPreferencias(preferenciasNoValidas);

        assertTrue(resultados.isEmpty(), "No debería devolver recetas si los filtros no coinciden con ninguna");
    }
}