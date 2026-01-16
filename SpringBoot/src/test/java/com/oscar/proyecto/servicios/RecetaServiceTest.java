package com.oscar.proyecto.servicios;

import com.oscar.proyecto.dto.Receta.RecetaResponseDTO;
import com.oscar.proyecto.modelos.Receta;
import com.oscar.proyecto.repositorios.RecetaRepository;
import org.junit.jupiter.api.BeforeEach;
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
    private RecetaRepository recetaRepository;

    private Integer idExistente;

    @BeforeEach
    void cargarDatos() {
        recetaRepository.deleteAll();

        Receta receta = new Receta();
        receta.setTitulo("Pizza");
        receta.setDescripcion("Pizza muy rica");
        receta.setTutorial("Paso a paso de la pizza");
        receta.setTiempoPreparacion(100);
        receta.setFotoUrl("https://picsum.photos/2000/300");

        Receta guardada = recetaRepository.save(receta);
        this.idExistente = guardada.getId();
    }

    @Test
    public void getRecetaByIdTest() {
        RecetaResponseDTO dto = recetaService.getRecetaById(idExistente);

        assertNotNull(dto, "La receta debería existir");
        assertEquals("Pizza", dto.getTitulo());
    }

    @Test
    public void getRecetaByIdNegativoTest() {
        assertThrows(RuntimeException.class, () -> {
            recetaService.getRecetaById(999);
        }, "Debería lanzar una excepción si la receta no existe");
    }

}