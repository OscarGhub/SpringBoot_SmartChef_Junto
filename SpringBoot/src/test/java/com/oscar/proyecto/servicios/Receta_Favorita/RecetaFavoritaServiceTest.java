package com.oscar.proyecto.servicios.Receta_Favorita;

import com.oscar.proyecto.dto.Receta.RecetaResponseDTO;
import com.oscar.proyecto.modelos.Receta;
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
public class RecetaFavoritaServiceTest {

    @Autowired
    private RecetaService service;

    @Autowired
    private RecetaRepository recetaRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private RecetaGuardadaRepository recetaGuardadaRepo;

    private Integer usuarioId;
    private Integer recetaId;

    @BeforeAll
    void cargarDatos() {
        Usuario u = new Usuario();
        u.setNombre("Oscar Favoritos");
        u.setCorreoElectronico("oscar.fav@test.com");
        u.setContrasena("123");
        u = usuarioRepo.save(u);
        this.usuarioId = u.getId();

        Receta r = new Receta();
        r.setTitulo("Paella");
        r.setDescripcion("Arroz con cosas");
        r = recetaRepo.save(r);
        this.recetaId = r.getId();
    }

    @Test
    @DisplayName("Test Unitario Positivo -> Guardar Receta como Favorita")
    public void guardarRecetaFavoritaTest() {

        RecetaResponseDTO resultado = service.guardarReceta(this.recetaId, this.usuarioId);

        assertNotNull(resultado, "La respuesta no debería ser nula");
        assertEquals("Paella", resultado.getTitulo());
        assertTrue(resultado.getGuardada(), "La receta debería marcarse como guardada (favorita)");

        long totalFavoritos = recetaGuardadaRepo.contarGuardados(this.recetaId);
        assertTrue(totalFavoritos >= 1, "Debería haber al menos un registro en la tabla de favoritos");
    }

    @Test
    @DisplayName("Test Unitario Negativo -> Receta inexistente")
    public void guardarRecetaInexistenteTest() {
        Integer recetaIdFalsa = 9999;

        assertThrows(Exception.class, () -> {
            service.guardarReceta(recetaIdFalsa, this.usuarioId);
        }, "Debería fallar al intentar marcar como favorita una receta que no existe");
    }
}
