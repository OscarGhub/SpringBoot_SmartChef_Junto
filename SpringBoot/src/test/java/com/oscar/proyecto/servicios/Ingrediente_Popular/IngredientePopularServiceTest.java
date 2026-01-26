package com.oscar.proyecto.servicios.Ingrediente_Popular;

import com.oscar.proyecto.dto.Ingrediente.TopIngredienteDTO;
import com.oscar.proyecto.modelos.Ingrediente;
import com.oscar.proyecto.modelos.Receta;
import com.oscar.proyecto.modelos.RecetaIngrediente;
import com.oscar.proyecto.modelos.RecetaIngredienteId;
import com.oscar.proyecto.repositorios.IngredienteRepository;
import com.oscar.proyecto.repositorios.RecetaIngredienteRepository;
import com.oscar.proyecto.repositorios.RecetaRepository;
import com.oscar.proyecto.servicios.IngredienteService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IngredientePopularServiceTest {

    @Autowired
    private IngredienteService service;

    @Autowired
    private IngredienteRepository ingredienteRepo;

    @Autowired
    private RecetaRepository recetaRepo;

    @Autowired
    private RecetaIngredienteRepository recetaIngredienteRepo;

    @BeforeAll
    void cargarDatos() {
        Ingrediente i1 = new Ingrediente();
        i1.setNombre("Sal");
        i1 = ingredienteRepo.save(i1);

        Ingrediente i2 = new Ingrediente();
        i2.setNombre("Aceite de Oliva");
        i2 = ingredienteRepo.save(i2);

        Receta r = new Receta();
        r.setTitulo("Receta de Prueba");
        r = recetaRepo.save(r);

        RecetaIngrediente rel1 = new RecetaIngrediente();
        rel1.setId(new RecetaIngredienteId(r.getId(), i1.getId()));
        rel1.setReceta(r);
        rel1.setIngrediente(i1);
        recetaIngredienteRepo.save(rel1);

        RecetaIngrediente rel2 = new RecetaIngrediente();
        rel2.setId(new RecetaIngredienteId(r.getId(), i2.getId()));
        rel2.setReceta(r);
        rel2.setIngrediente(i2);
        recetaIngredienteRepo.save(rel2);
    }

    @Test
    @DisplayName("Test Unitario Positivo -> Obtener Top Ingredientes")
    public void getTop5IngredientesTest() {
        List<TopIngredienteDTO> resultado = service.getTop5Ingredientes();

        assertNotNull(resultado, "La lista no debería ser nula");
        assertFalse(resultado.isEmpty(), "Debería haber datos en el top tras vincular ingredientes");

        boolean contieneAceite = resultado.stream()
                .anyMatch(dto -> dto.getIngrediente().equals("Aceite de Oliva"));

        assertTrue(contieneAceite, "El aceite de oliva debería aparecer en el top");
    }

    @Test
    @DisplayName("Test Unitario Negativo -> Lista no nula")
    public void getTop5IngredientesVacioTest() {
        List<TopIngredienteDTO> resultado = service.getTop5Ingredientes();
        assertNotNull(resultado, "El service debería devolver una lista vacía, nunca null");
    }
}
