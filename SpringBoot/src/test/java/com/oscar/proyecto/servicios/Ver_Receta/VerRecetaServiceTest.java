package com.oscar.proyecto.servicios.Ver_Receta;

import com.oscar.proyecto.dto.Receta.RecetaResponseDTO;
import com.oscar.proyecto.modelos.Receta;
import com.oscar.proyecto.servicios.RecetaService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class VerRecetaServiceTest {

    @Autowired
    private RecetaService recetaService;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Servicio Ver Receta -> Caso Positivo: Obtener detalle por ID")
    public void testGetRecetaByIdExitoso() {
        Receta receta = new Receta();
        receta.setTitulo("Gazpacho Andaluz");
        receta.setTutorial("Paso 1: Triturar verduras...");
        entityManager.persist(receta);
        entityManager.flush();

        Integer idGenerado = receta.getId();

        RecetaResponseDTO resultado = recetaService.getRecetaById(idGenerado);

        assertNotNull(resultado, "El DTO de respuesta no debería ser nulo");
        assertEquals(idGenerado, resultado.getId());
        assertEquals("Gazpacho Andaluz", resultado.getTitulo());

        assertEquals(0, resultado.getNumFavoritos());
    }

    @Test
    @DisplayName("Servicio Ver Receta -> Caso Negativo: ID inexistente")
    public void testGetRecetaByIdNoEncontrado() {
        Integer idInexistente = 9999;

        RecetaResponseDTO resultado = recetaService.getRecetaById(idInexistente);

        assertNull(resultado, "Debería devolver null si la receta no existe en la base de datos");
    }
}