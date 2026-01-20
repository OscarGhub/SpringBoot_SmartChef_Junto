package com.oscar.proyecto.servicios.Lista_Compra;

import com.oscar.proyecto.modelos.*;
import com.oscar.proyecto.repositorios.*;
import com.oscar.proyecto.servicios.ListaCompraService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
public class ListaCompraServiceIntegrationTest {

    @InjectMocks
    private ListaCompraService listaCompraService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RecetaRepository recetaRepository;

    @Mock
    private RecetaIngredienteRepository recetaIngredienteRepository;

    @Mock
    private ListaCompraRepository listaCompraRepository;

    @Mock
    private ListaCompraIngredienteRepository listaCompraIngredienteRepository;

    @Test
    @DisplayName("Test Integrado Positivo -> Añadir ingredientes al carrito")
    public void testAnadirRecetaAlCarritoExitoso() {
        Integer userId = 1;
        Integer recId = 10;

        Usuario usuario = new Usuario();
        usuario.setId(userId);

        Receta receta = new Receta();
        receta.setId(recId);

        Ingrediente tomate = new Ingrediente();
        tomate.setId(5);
        tomate.setNombre("Tomate");

        RecetaIngrediente ri = new RecetaIngrediente();
        ri.setReceta(receta);
        ri.setIngrediente(tomate);
        ri.setCantidad(2.0);

        ListaCompra lista = new ListaCompra();
        lista.setUsuario(usuario);

        Mockito.lenient().when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuario));
        Mockito.lenient().when(recetaRepository.findById(anyInt())).thenReturn(Optional.of(receta));

        Mockito.when(recetaIngredienteRepository.findByRecetaIdEagerly(recId)).thenReturn(List.of(ri));

        Mockito.when(listaCompraRepository.findByUsuarioId(userId)).thenReturn(Optional.of(lista));

        Mockito.lenient().when(listaCompraIngredienteRepository.save(any())).thenReturn(new ListaCompraIngrediente());

        listaCompraService.anadirRecetaAlCarrito(userId, recId);

        Mockito.verify(recetaIngredienteRepository).findByRecetaIdEagerly(recId);

        Mockito.verify(listaCompraIngredienteRepository, Mockito.atLeastOnce()).save(any());
    }
}