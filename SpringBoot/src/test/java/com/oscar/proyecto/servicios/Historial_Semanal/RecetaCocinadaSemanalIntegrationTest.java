package com.oscar.proyecto.servicios.Historial_Semanal;

import com.oscar.proyecto.dto.Receta.RecetaUsoDTO;
import com.oscar.proyecto.modelos.Receta;
import com.oscar.proyecto.modelos.RecetaCocinadaFecha;
import com.oscar.proyecto.modelos.Usuario;
import com.oscar.proyecto.repositorios.RecetaCocinadaFechaRepository;
import com.oscar.proyecto.repositorios.RecetaRepository;
import com.oscar.proyecto.repositorios.UsuarioRepository;
import com.oscar.proyecto.servicios.RecetaCocinadaFechaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class RecetaCocinadaSemanalIntegrationTest {

    @Autowired
    private RecetaCocinadaFechaService service;

    @Autowired
    private RecetaCocinadaFechaRepository cocinadaRepo;

    @Autowired
    private RecetaRepository recetaRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @BeforeEach
    void setup() {
        Usuario u = new Usuario();
        u.setNombre("Oscar");
        u.setCorreoElectronico("oscar@semanal.com");
        u.setContrasena("123456");
        usuarioRepo.save(u);

        Receta r1 = new Receta();
        r1.setTitulo("Pasta");
        recetaRepo.save(r1);

        Receta r2 = new Receta();
        r2.setTitulo("Ensalada");
        recetaRepo.save(r2);

        RecetaCocinadaFecha hoy = new RecetaCocinadaFecha();
        hoy.setUsuario(u);
        hoy.setReceta(r1);
        hoy.setFechaCocinado(LocalDate.now());
        cocinadaRepo.save(hoy);

        RecetaCocinadaFecha antigua = new RecetaCocinadaFecha();
        antigua.setUsuario(u);
        antigua.setReceta(r2);
        antigua.setFechaCocinado(LocalDate.now().minusDays(10));
        cocinadaRepo.save(antigua);
    }

    @Test
    @DisplayName("Test Integración Positivo: Consultar historial semanal, filtra correctamente por fecha")
    public void testGetRecetasUltimaSemana() {
        List<RecetaUsoDTO> resultados = service.getRecetasUltimaSemana();

        assertNotNull(resultados);
        assertEquals(1, resultados.size(), "Debería haber solo una receta en la última semana");
        assertEquals("Pasta", resultados.get(0).getNombreReceta());

        boolean contieneEnsalada = resultados.stream()
                .anyMatch(r -> r.getNombreReceta().equals("Ensalada"));
        assertFalse(contieneEnsalada, "La ensalada cocinada hace 10 días no debería aparecer");
    }
}