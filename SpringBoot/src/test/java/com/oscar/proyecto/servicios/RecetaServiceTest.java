package com.oscar.proyecto.servicios;

import com.oscar.proyecto.dto.Receta.RecetaRequestDTO;
import com.oscar.proyecto.dto.Receta.RecetaResponseDTO;
import com.oscar.proyecto.modelos.Usuario;
import com.oscar.proyecto.repositorios.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class RecetaServiceTest {

    @Autowired
    private RecetaService recetaService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Positivo: Agregar una nueva receta con ingredientes y pasos")
    public void testAgregarRecetaExitoso() {

        RecetaRequestDTO nuevaReceta = new RecetaRequestDTO();
        nuevaReceta.setTitulo("Tortilla de Patatas");
        nuevaReceta.setDescripcion("4 patatas, 6 huevos, sal");
        nuevaReceta.setTutorial("1. Freír patatas. 2. Batir huevos. 3. Mezclar y cuajar.");

        RecetaResponseDTO guardada = recetaService.crearReceta(nuevaReceta);

        assertNotNull(guardada, "La receta debería haberse guardado");
        assertEquals("Tortilla de Patatas", guardada.getTitulo());
        assertNotNull(guardada.getId(), "La receta debería tener un ID asignado");
    }

    @Test
    @DisplayName("Negativo: Agregar Receta - Faltan pasos")
    public void testAgregarRecetaFaltanPasos() {
        RecetaRequestDTO recetaSinPasos = new RecetaRequestDTO();
        recetaSinPasos.setTitulo("Receta incompleta");
        recetaSinPasos.setDescripcion("Ingrediente 1");
        recetaSinPasos.setTutorial(null);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            recetaService.crearReceta(recetaSinPasos);
        });

        assertTrue(exception.getMessage().contains("pasos faltan"),
                "Debe saltar una excepción mencionando que pasos faltan");
    }

    @Test
    @DisplayName("Negativo: Agregar Receta - Formato de ingredientes incorrecto")
    public void testAgregarRecetaIngredientesFormatoIncorrecto() {
        RecetaRequestDTO recetaFormatoMal = new RecetaRequestDTO();
        recetaFormatoMal.setTitulo("Receta mal formato");
        recetaFormatoMal.setDescripcion("!!!");
        recetaFormatoMal.setTutorial("Paso 1...");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            recetaService.crearReceta(recetaFormatoMal);
        });

        assertTrue(exception.getMessage().toLowerCase().contains("formato") ||
                exception.getMessage().toLowerCase().contains("incorrecto"));
    }
}