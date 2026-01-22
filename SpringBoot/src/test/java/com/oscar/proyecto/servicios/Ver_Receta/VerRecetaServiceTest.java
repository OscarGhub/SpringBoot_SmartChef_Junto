package com.oscar.proyecto.servicios.Ver_Receta;

import com.oscar.proyecto.dto.Receta.RecetaResponseDTO;
import com.oscar.proyecto.modelos.Receta;
import com.oscar.proyecto.repositorios.RecetaRepository;
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
public class VerRecetaServiceTest {

    @Autowired
    private RecetaService recetaService;

    @Autowired
    private RecetaRepository recetaRepo;

    private Integer idGenerado;

    @BeforeAll
    void cargarDatos() {
        Receta receta = new Receta();
        receta.setTitulo("Gazpacho Andaluz");
        receta.setTutorial("Paso 1: Triturar verduras...");
        receta.setDescripcion("Refrescante sopa fría");

        receta = recetaRepo.save(receta);
        this.idGenerado = receta.getId();
    }

    @Test
    @DisplayName("Servicio Ver Receta -> Caso Positivo: Obtener detalle por ID")
    public void testGetRecetaByIdExitoso() {
        RecetaResponseDTO resultado = recetaService.getRecetaById(this.idGenerado);

        assertNotNull(resultado, "El DTO de respuesta no debería ser nulo");
        assertEquals(this.idGenerado, resultado.getId(), "El ID debe coincidir con el generado por la BD");
        assertEquals("Gazpacho Andaluz", resultado.getTitulo());
        assertEquals("Paso 1: Triturar verduras...", resultado.getTutorial());
    }

    @Test
    @DisplayName("Servicio Ver Receta -> Caso Negativo: ID inexistente")
    public void testGetRecetaByIdNoEncontrado() {
        Integer idInexistente = 9999;

        RecetaResponseDTO resultado = recetaService.getRecetaById(idInexistente);

        assertNull(resultado, "Debería devolver null si la receta no existe en la base de datos");
    }
}
