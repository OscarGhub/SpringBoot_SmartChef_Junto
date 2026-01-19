package com.oscar.proyecto.servicios.Receta_Filtro;

import com.oscar.proyecto.dto.Receta.RecetaResponseDTO;
import com.oscar.proyecto.modelos.Receta;
import com.oscar.proyecto.modelos.Usuario;
import com.oscar.proyecto.modelos.RecetaGuardada;
import com.oscar.proyecto.modelos.RecetaGuardadaId;
import com.oscar.proyecto.repositorios.RecetaRepository;
import com.oscar.proyecto.repositorios.UsuarioRepository;
import com.oscar.proyecto.repositorios.RecetaGuardadaRepository;
import com.oscar.proyecto.servicios.RecetaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class RecetaFiltroServiceIntegrationTest {

    @Autowired
    private RecetaService recetaService;

    @Autowired
    private RecetaRepository recetaRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private RecetaGuardadaRepository recetaGuardadaRepo;

    @BeforeEach
    void cargarDatos() {
        Receta receta = new Receta();
        receta.setTitulo("Pasta Carbonara");
        receta.setTiempoPreparacion(20);
        receta = recetaRepo.save(receta);

        Usuario user = new Usuario();
        user.setNombre("Oscar");
        user.setCorreoElectronico("oscar@test.com");
        user.setContrasena("123456");
        user = usuarioRepo.save(user);

        RecetaGuardadaId rgId = new RecetaGuardadaId(user.getId(), receta.getId());
        RecetaGuardada rg = new RecetaGuardada();
        rg.setId(rgId);
        rg.setUsuario(user);
        rg.setReceta(receta);
        recetaGuardadaRepo.save(rg);
    }

    @Test
    @DisplayName("Test Integración Positivo: Filtrar por preferencias y verificar contador de favoritos")
    public void testFiltrarPorPreferenciasYFavoritos() {

        List<RecetaResponseDTO> resultados = recetaService.filtrarRecetasPorPreferencias(null);

        assertFalse(resultados.isEmpty());
        RecetaResponseDTO dto = resultados.get(0);

        assertEquals(1, dto.getNumFavoritos(), "La receta debería tener 1 favorito");
        assertEquals("Pasta Carbonara", dto.getTitulo());
    }

}