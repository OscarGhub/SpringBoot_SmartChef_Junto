package com.oscar.proyecto.servicios.Historial_Semanal;

import com.oscar.proyecto.dto.Receta.RecetaUsoDTO;
import com.oscar.proyecto.modelos.Receta;
import com.oscar.proyecto.modelos.RecetaCocinadaFecha;
import com.oscar.proyecto.modelos.Usuario;
import com.oscar.proyecto.repositorios.RecetaCocinadaFechaRepository;
import com.oscar.proyecto.repositorios.RecetaRepository;
import com.oscar.proyecto.repositorios.UsuarioRepository;
import com.oscar.proyecto.servicios.RecetaCocinadaFechaService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RecetaCocinadaSemanalTest {

    @Autowired
    private RecetaCocinadaFechaService service;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private RecetaRepository recetaRepo;

    @Autowired
    private RecetaCocinadaFechaRepository historialRepo;

    @BeforeAll
    void cargarDatos() {
        Usuario u = new Usuario();
        u.setNombre("Chef Oscar");
        u.setCorreoElectronico("oscar@semana.com");
        u.setContrasena("123");
        u = usuarioRepo.save(u);

        Receta r = new Receta();
        r.setTitulo("Pasta");
        r = recetaRepo.save(r);

        Receta rAntigua = new Receta();
        rAntigua.setTitulo("Receta Antigua");
        rAntigua = recetaRepo.save(rAntigua);

        RecetaCocinadaFecha registroHoy = new RecetaCocinadaFecha();
        registroHoy.setUsuario(u);
        registroHoy.setReceta(r);
        registroHoy.setFechaCocinado(LocalDate.now());
        historialRepo.save(registroHoy);

        RecetaCocinadaFecha registroAntiguo = new RecetaCocinadaFecha();
        registroAntiguo.setUsuario(u);
        registroAntiguo.setReceta(rAntigua);
        registroAntiguo.setFechaCocinado(LocalDate.now().minusDays(15));
        historialRepo.save(registroAntiguo);
    }

    @Test
    @DisplayName("Test Unitario Positivo -> Consultar recetas cocinadas esta semana")
    public void testGetRecetasUltimaSemanaReal() {
        List<RecetaUsoDTO> resultados = service.getRecetasUltimaSemana();

        assertNotNull(resultados, "La lista no debería ser nula");
        assertFalse(resultados.isEmpty(), "Debería haber al menos un registro de esta semana");

        boolean contienePasta = resultados.stream()
                .anyMatch(dto -> dto.getNombreReceta().equals("Pasta"));

        assertTrue(contienePasta, "La lista debería contener la receta 'Pasta'");
    }

    @Test
    @DisplayName("Test Unitario Negativo -> No mostrar registros fuera de rango (15 días)")
    public void testGetRecetasUltimaSemanaFueraDeRango() {
        List<RecetaUsoDTO> resultados = service.getRecetasUltimaSemana();

        boolean existeRecetaAntigua = resultados.stream()
                .anyMatch(dto -> dto.getNombreReceta().equals("Receta Antigua"));

        assertFalse(existeRecetaAntigua, "No debería mostrar recetas cocinadas hace más de 7 días");
    }
}