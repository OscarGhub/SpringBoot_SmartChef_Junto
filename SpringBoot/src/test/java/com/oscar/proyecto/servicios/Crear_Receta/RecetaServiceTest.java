package com.oscar.proyecto.servicios.Crear_Receta;

import com.oscar.proyecto.dto.Receta.RecetaRequestDTO;
import com.oscar.proyecto.dto.Receta.RecetaResponseDTO;
import com.oscar.proyecto.modelos.Receta;
import com.oscar.proyecto.servicios.RecetaService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class RecetaServiceTest {

    @Autowired
    private RecetaService service;

    @BeforeAll
    static void cargarDatos() {

        Receta r = new Receta();
        r.setTitulo("Receta de Prueba");
        r.setDescripcion("Descripción de prueba");
        r.setTiempoPreparacion(30);

        Receta r2 = new Receta();
        r2.setTitulo("Receta de Prueba 2");
        r2.setDescripcion("Descripción de prueba 2");
        r2.setTiempoPreparacion(45);

    }

    @Test
    @DisplayName("Test Unitario Positivo -> Crear Receta")
    public void crearRecetaTest() {
        RecetaRequestDTO dto = new RecetaRequestDTO();
        dto.setTitulo("Paella Valenciana");

        RecetaResponseDTO resultado = service.crearReceta(dto);

        assertNotNull(resultado, "La receta creada no debería ser nula");
        assertEquals("Paella Valenciana", resultado.getTitulo(), "El título de la receta no coincide");
    }

    @Test
    @DisplayName("Test Unitario Negativo -> No se introdujo título a la receta")
    public void crearRecetaNegativoTest() {
        RecetaRequestDTO recetaInvalida = new RecetaRequestDTO();
        recetaInvalida.setTitulo(null);

        assertThrows(Exception.class, () -> service.crearReceta(recetaInvalida),
                "El sistema debería haber fallado al intentar crear una receta sin título");
    }
}