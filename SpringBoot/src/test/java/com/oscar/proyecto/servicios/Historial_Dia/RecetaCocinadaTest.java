package com.oscar.proyecto.servicios.Historial_Dia;

import com.oscar.proyecto.dto.Receta.RecetaUsoDTO;
import com.oscar.proyecto.dto.Receta.RecetaUsoRequestDTO;
import com.oscar.proyecto.exception.ElementoNoEncontradoException;
import com.oscar.proyecto.modelos.Receta;
import com.oscar.proyecto.modelos.RecetaCocinadaFecha;
import com.oscar.proyecto.modelos.Usuario;
import com.oscar.proyecto.servicios.RecetaCocinadaFechaService;
import jakarta.persistence.EntityManager;
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
public class RecetaCocinadaTest {

    @Autowired
    private RecetaCocinadaFechaService service;

    @Autowired
    private EntityManager entityManager;

    private Integer usuarioId;
    private Integer recetaId;

    @BeforeEach
    void prepararDatos() {
        Usuario u = new Usuario();
        u.setNombre("Oscar");
        u.setCorreoElectronico("oscar@test.com");
        u.setContrasena("123");
        entityManager.persist(u);
        this.usuarioId = u.getId();

        Receta r = new Receta();
        r.setTitulo("Tortilla de Patatas");
        entityManager.persist(r);
        this.recetaId = r.getId();

        entityManager.flush();
    }

    @Test
    @DisplayName("Servicio RecetaCocinada -> Caso Positivo: Guardar registro")
    public void testGuardarRecetaEnFechaExitoso() {
        RecetaUsoRequestDTO dto = new RecetaUsoRequestDTO();
        dto.setIdUsuario(this.usuarioId);
        dto.setIdReceta(this.recetaId);
        dto.setFecha(LocalDate.now());

        RecetaUsoDTO resultado = service.guardarRecetaEnFecha(dto);

        assertNotNull(resultado);
        assertEquals("Tortilla de Patatas", resultado.getNombreReceta());

        RecetaCocinadaFecha guardado = entityManager.createQuery(
                        "SELECT r FROM RecetaCocinadaFecha r WHERE r.usuario.id = :uId AND r.receta.id = :rId",
                        RecetaCocinadaFecha.class)
                .setParameter("uId", this.usuarioId)
                .setParameter("rId", this.recetaId)
                .getSingleResult();

        assertNotNull(guardado, "El registro debería existir en la base de datos");
    }

    @Test
    @DisplayName("Servicio RecetaCocinada -> Caso Negativo: Receta inexistente")
    public void testRecetaNoEncontrada() {
        RecetaUsoRequestDTO dto = new RecetaUsoRequestDTO();
        dto.setIdUsuario(this.usuarioId);
        dto.setIdReceta(999);
        dto.setFecha(LocalDate.now());

        assertThrows(ElementoNoEncontradoException.class, () -> {
            service.guardarRecetaEnFecha(dto);
        }, "Debería lanzar ElementoNoEncontradoException si la receta no existe en BD");
    }
}