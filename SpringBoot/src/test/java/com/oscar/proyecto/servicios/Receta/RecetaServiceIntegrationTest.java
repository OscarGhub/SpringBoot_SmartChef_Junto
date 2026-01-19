package com.oscar.proyecto.servicios.Receta;

import com.oscar.proyecto.dto.Receta.RecetaRequestDTO;
import com.oscar.proyecto.dto.Receta.RecetaResponseDTO;
import com.oscar.proyecto.repositorios.UsuarioRepository;
import com.oscar.proyecto.servicios.RecetaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class RecetaServiceIntegrationTest {

    @Autowired
    private RecetaService recetaService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Test Integrado Positivo: Agregar Receta con ingredientes y pasos")
    public void testIntegradoAgregarRecetaExitoso() {

        RecetaRequestDTO nuevaReceta = new RecetaRequestDTO();
        nuevaReceta.setTitulo("Tortilla de Patatas");
        nuevaReceta.setDescripcion("4 patatas, 6 huevos, sal");
        nuevaReceta.setTutorial("1. Freír patatas. 2. Batir huevos. 3. Mezclar y cuajar.");

        RecetaResponseDTO guardada = recetaService.crearReceta(nuevaReceta);

        assertNotNull(guardada, "La receta debería haberse guardado");
        assertEquals("Tortilla de Patatas", guardada.getTitulo());
        assertNotNull(guardada.getId(), "La receta debería tener un ID asignado");
    }
}