package com.oscar.proyecto.servicios.Historial_Dia;

import com.oscar.proyecto.dto.Receta.RecetaUsoDTO;
import com.oscar.proyecto.dto.Receta.RecetaUsoRequestDTO;
import com.oscar.proyecto.exception.ElementoNoEncontradoException;
import com.oscar.proyecto.modelos.Receta;
import com.oscar.proyecto.modelos.Usuario;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RecetaCocinadaTest {

    @Autowired
    private RecetaCocinadaFechaService service;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private RecetaRepository recetaRepo;

    private Integer usuarioId;
    private Integer recetaId;

    @BeforeAll
    void cargarDatos() {
        Usuario u = new Usuario();
        u.setNombre("Oscar");
        u.setCorreoElectronico("oscar@test.com");
        u.setContrasena("123");
        u = usuarioRepo.save(u);
        this.usuarioId = u.getId();

        Receta r = new Receta();
        r.setTitulo("Tortilla de Patatas");
        r = recetaRepo.save(r);
        this.recetaId = r.getId();
    }

    @Test
    @DisplayName("Test Unitario Positivo -> Guardar receta con fecha actual")
    public void testGuardarRecetaEnFechaExitoso() {
        RecetaUsoRequestDTO dto = new RecetaUsoRequestDTO();
        dto.setIdUsuario(this.usuarioId);
        dto.setIdReceta(this.recetaId);
        dto.setFecha(LocalDate.now());

        RecetaUsoDTO resultado = service.guardarRecetaEnFecha(dto);

        assertNotNull(resultado, "El registro de cocinado no debería ser nulo");
        assertEquals("Tortilla de Patatas", resultado.getNombreReceta());
    }

    @Test
    @DisplayName("Test Unitario Negativo -> No se ha encontrado la receta con ese ID")
    public void testRecetaNoEncontrada() {
        RecetaUsoRequestDTO dto = new RecetaUsoRequestDTO();
        dto.setIdUsuario(this.usuarioId);
        dto.setIdReceta(999);
        dto.setFecha(LocalDate.now());

        assertThrows(ElementoNoEncontradoException.class, () -> service.guardarRecetaEnFecha(dto));
    }
}