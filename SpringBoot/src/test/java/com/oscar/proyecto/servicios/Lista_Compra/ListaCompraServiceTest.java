package com.oscar.proyecto.servicios.Lista_Compra;

import com.oscar.proyecto.modelos.*;
import com.oscar.proyecto.repositorios.*;
import com.oscar.proyecto.servicios.ListaCompraService;
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
public class ListaCompraServiceTest {

    @Autowired
    private ListaCompraService service;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private RecetaRepository recetaRepo;

    @Autowired
    private IngredienteRepository ingredienteRepo;

    @Autowired
    private ListaCompraRepository listaCompraRepo;

    @Autowired
    private RecetaIngredienteRepository recetaIngredienteRepo;

    @Autowired
    private ListaCompraIngredienteRepository listaCompraIngredienteRepo;

    private Integer usuarioId;
    private Integer recetaIdConIngredientes;
    private Integer recetaIdVacia;

    @BeforeAll
    void cargarDatos() {
        Usuario u = new Usuario();
        u.setNombre("Comprador");
        u.setCorreoElectronico("compras@test.com");
        u.setContrasena("123");
        u = usuarioRepo.save(u);
        this.usuarioId = u.getId();

        ListaCompra lc = new ListaCompra();
        lc.setUsuario(u);
        listaCompraRepo.save(lc);

        Ingrediente tomate = new Ingrediente();
        tomate.setNombre("Tomate");
        tomate = ingredienteRepo.save(tomate);

        Receta r1 = new Receta();
        r1.setTitulo("Receta con Tomate");
        r1 = recetaRepo.save(r1);
        this.recetaIdConIngredientes = r1.getId();

        RecetaIngrediente ri = new RecetaIngrediente();
        ri.setId(new RecetaIngredienteId(r1.getId(), tomate.getId()));
        ri.setReceta(r1);
        ri.setIngrediente(tomate);
        ri.setCantidad(2.0);
        recetaIngredienteRepo.save(ri);

        Receta r2 = new Receta();
        r2.setTitulo("Receta Vacía");
        r2 = recetaRepo.save(r2);
        this.recetaIdVacia = r2.getId();
    }

    @Test
    @DisplayName("Test Unitario Positivo -> Añadir receta al carrito")
    public void testAnadirRecetaAlCarritoUnitario() {
        service.anadirRecetaAlCarrito(this.usuarioId, this.recetaIdConIngredientes);

        List<ListaCompraIngrediente> itemsCarrito = listaCompraIngredienteRepo.findAll();

        assertFalse(itemsCarrito.isEmpty(), "El carrito no debería estar vacío");

        boolean tieneTomate = itemsCarrito.stream()
                .anyMatch(item -> item.getIngrediente().getNombre().equals("Tomate") && item.getCantidad() == 2.0);

        assertTrue(tieneTomate, "El tomate con cantidad 2.0 debería estar en la lista de compra");
    }

    @Test
    @DisplayName("Test Unitario Negativo -> No hace nada si la receta no tiene ingredientes")
    public void testAnadirRecetaVaciaAlCarrito() {
        int cantidadAntes = listaCompraIngredienteRepo.findAll().size();

        service.anadirRecetaAlCarrito(this.usuarioId, this.recetaIdVacia);

        int cantidadDespues = listaCompraIngredienteRepo.findAll().size();
        assertEquals(cantidadAntes, cantidadDespues, "No deberían haberse añadido ingredientes al carrito");
    }
}
