package com.oscar.proyecto.servicios.Historial_Dia;

import com.oscar.proyecto.dto.Receta.RecetaUsoRequestDTO;
import com.oscar.proyecto.dto.Receta.RecetaUsoDTO;
import com.oscar.proyecto.modelos.Receta;
import com.oscar.proyecto.modelos.Usuario;
import com.oscar.proyecto.repositorios.RecetaRepository;
import com.oscar.proyecto.repositorios.UsuarioRepository;
import com.oscar.proyecto.repositorios.RecetaCocinadaFechaRepository;
import com.oscar.proyecto.servicios.RecetaCocinadaFechaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class RecetaCocinadaIntegrationTest {

    @Autowired
    private RecetaCocinadaFechaService service;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private RecetaRepository recetaRepo;

    @Autowired
    private RecetaCocinadaFechaRepository cocinadaRepo;

    private Integer userId;
    private Integer recetaId;

    @BeforeEach
    void cargarDatos() {
        Usuario u = new Usuario();
        u.setNombre("Cocinero");
        u.setCorreoElectronico("test@cocina.com");
        u.setContrasena("hash_123");
        userId = usuarioRepo.save(u).getId();

        Receta r = new Receta();
        r.setTitulo("Tortilla");
        recetaId = recetaRepo.save(r).getId();
    }

    @Test
    @DisplayName("Test Integración Positivo: Guardar receta en fecha correctamente")
    public void testGuardarRecetaEnFechaExitoso() {
        RecetaUsoRequestDTO request = new RecetaUsoRequestDTO();
        request.setIdUsuario(userId);
        request.setIdReceta(recetaId);
        request.setFecha(LocalDate.now());

        RecetaUsoDTO response = service.guardarRecetaEnFecha(request);

        assertNotNull(response);
        assertEquals("Tortilla", response.getNombreReceta());
        assertTrue(cocinadaRepo.count() > 0, "Debe existir un registro en la tabla de historial");
    }
}