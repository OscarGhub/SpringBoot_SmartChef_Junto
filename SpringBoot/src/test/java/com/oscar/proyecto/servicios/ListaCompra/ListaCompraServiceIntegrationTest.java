package com.oscar.proyecto.servicios.ListaCompra;

import com.oscar.proyecto.modelos.*;
import com.oscar.proyecto.repositorios.*;
import com.oscar.proyecto.servicios.ListaCompraService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ListaCompraServiceIntegrationTest {

    @Autowired
    private ListaCompraService listaCompraService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RecetaRepository recetaRepository;

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Autowired
    private RecetaIngredienteRepository recetaIngredienteRepository;

    @Autowired
    private ListaCompraRepository listaCompraRepository;

    @Autowired
    private ListaCompraIngredienteRepository listaCompraIngredienteRepository;

    private Integer usuarioId;
    private Integer recetaId;

    @BeforeEach
    void cargarDatos() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Chef Oscar");
        usuario.setCorreoElectronico("oscar@cocina.com");
        usuario.setContrasena("123456");
        usuario = usuarioRepository.save(usuario);
        usuarioId = usuario.getId();

        Ingrediente tomate = new Ingrediente();
        tomate.setNombre("Tomate");
        tomate = ingredienteRepository.save(tomate);

        Receta receta = new Receta();
        receta.setTitulo("Salsa Base");
        receta = recetaRepository.save(receta);
        recetaId = receta.getId();

        RecetaIngrediente ri = new RecetaIngrediente();

        RecetaIngredienteId idCompuesto = new RecetaIngredienteId(receta.getId(), tomate.getId());
        ri.setId(idCompuesto);

        ri.setReceta(receta);
        ri.setIngrediente(tomate);
        ri.setCantidad(2.0);

        recetaIngredienteRepository.save(ri);
    }

    @Test
    @DisplayName("Test Integrado Positivo: Añadir ingredientes de una receta al carrito del usuario")
    public void testAnadirRecetaAlCarritoExitoso() {
        listaCompraService.anadirRecetaAlCarrito(usuarioId, recetaId);

        ListaCompra lista = listaCompraRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new AssertionError("La lista de compra no se creó"));

        List<ListaCompraIngrediente> items = listaCompraIngredienteRepository.findByListaCompra(lista);

        assertFalse(items.isEmpty(), "La lista debería tener ingredientes");
    }
}