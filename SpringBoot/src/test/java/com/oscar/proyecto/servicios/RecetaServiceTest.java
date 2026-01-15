package com.oscar.proyecto.servicios;

import com.oscar.proyecto.dto.Receta.RecetaResponseDTO;
import com.oscar.proyecto.modelos.Receta;
import com.oscar.proyecto.repositorios.RecetaRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RecetaServiceTest {

    @Autowired
    private RecetaService recetaService;

    @BeforeAll
    static void cargarDatos() {

        Receta receta = new Receta();
        receta.setTitulo("Pizza");
        receta.setDescripcion("Pizza muy rica");
        receta.setTutorial("Paso a paso de la pizza");
        receta.setTiempoPreparacion(100);
        receta.setFotoUrl("https://picsum.photos/2000/300");

        Receta receta2 = new Receta();
        receta2.setTitulo("Magdalena");
        receta2.setDescripcion("Magdalena muy rica");
        receta2.setTutorial("Paso a paso de las magdalenas");
        receta2.setTiempoPreparacion(11);
        receta2.setFotoUrl("https://picsum.photos/200/300");

        repository.save(receta);
        repository.save(receta2);

    }

    @Test
    public void getRecetaByIdTest() {

        //Given (PREVIOS)

        //Then (EJECUCIÓN / PRUEBA DEL METODO)
        RecetaResponseDTO dto = recetaService.getRecetaById(1);

        //When (COMPROBACIONES)
        assertNotNull(dto, "La receta que busca no existe");
        assertEquals(dto.getTitulo(), "Pizza", "El titulo de la receta no es correcto");

    }

    @Test
    public void getRecetaByIdNegativoTest() {

        //Given (PREVIOS)

        //Then (EJECUCIÓN / PRUEBA DEL METODO)
        RecetaResponseDTO dto = recetaService.getRecetaById(1);

        //When (COMPROBACIONES)
        assertThrows(NullPointerException.class, () -> recetaService.getRecetaById(1)), "La receta que busca no existe")

    }
}
