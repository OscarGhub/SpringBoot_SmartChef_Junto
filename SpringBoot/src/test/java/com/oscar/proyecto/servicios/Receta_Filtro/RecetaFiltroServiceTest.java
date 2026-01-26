package com.oscar.proyecto.servicios.Receta_Filtro;

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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RecetaFiltroServiceTest {

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
        receta.setTitulo("Pasta Carbonara");
        receta = recetaRepo.save(receta);

        Usuario u = new Usuario();
        u.setNombre("Oscar");
        u.setCorreoElectronico("oscar@filtro.com");
        u.setContrasena("123");
        u = usuarioRepo.save(u);

        RecetaGuardada rg = new RecetaGuardada();
        rg.setId(new RecetaGuardadaId(u.getId(), receta.getId()));
        rg.setUsuario(u);
        rg.setReceta(receta);
        recetaGuardadaRepo.save(rg);
    }

    @Test
    @DisplayName("Test Unitario -> Caso Positivo: Filtrar sin preferencias (trae todas)")
    public void testFiltrarPorPreferenciasExitoso() {
        List<RecetaResponseDTO> resultados = recetaService.filtrarRecetasPorPreferencias(null);

        assertFalse(resultados.isEmpty(), "La lista no debería estar vacía");

        RecetaResponseDTO dto = resultados.stream()
                .filter(r -> r.getTitulo().equals("Pasta Carbonara"))
                .findFirst()
                .orElse(null);

        assertNotNull(dto);
        assertEquals(1, dto.getNumFavoritos(), "El contador de favoritos debería ser 1 basado en RecetaGuardada");
    }

    @Test
    @DisplayName("Test Unitario -> Caso Negativo: Filtrar con preferencias inexistentes")
    public void testFiltrarPorPreferenciasVacio() {
        List<Integer> preferenciasNoValidas = List.of(999);

        List<RecetaResponseDTO> resultados = recetaService.filtrarRecetasPorPreferencias(preferenciasNoValidas);

        assertTrue(resultados.isEmpty(), "No debería devolver recetas si los filtros no coinciden");
    }
}
