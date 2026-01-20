//package com.oscar.proyecto.servicios.Lista_Compra;
//
//import com.oscar.proyecto.modelos.*;
//import com.oscar.proyecto.repositorios.*;
//import com.oscar.proyecto.servicios.ListaCompraService;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//public class ListaCompraServiceTest {
//
//    @Autowired
//    private ListaCompraService listaCompraService;
//
//    @MockitoBean
//    private ListaCompraRepository listaCompraRepository;
//
//    @MockitoBean
//    private RecetaIngredienteRepository recetaIngredienteRepository;
//
//    @MockitoBean
//    private ListaCompraIngredienteRepository listaCompraIngredienteRepository;
//
//    @Test
//    @DisplayName("Test Unitario Positivo: Añadir receta al carrito")
//    public void testAnadirRecetaAlCarritoUnitario() {
//        Integer idUsuario = 1;
//        Integer idReceta = 50;
//
//        ListaCompra listaMock = new ListaCompra();
//        listaMock.setId(100);
//        when(listaCompraRepository.findByUsuarioId(idUsuario)).thenReturn(Optional.of(listaMock));
//
//        Ingrediente tomate = new Ingrediente();
//        tomate.setId(10);
//        tomate.setNombre("Tomate");
//
//        RecetaIngrediente ri = new RecetaIngrediente();
//        ri.setIngrediente(tomate);
//        ri.setCantidad(2.0);
//
//        when(recetaIngredienteRepository.findByRecetaIdEagerly(idReceta)).thenReturn(List.of(ri));
//
//        when(listaCompraIngredienteRepository.findById(any(ListaCompraIngredienteId.class)))
//                .thenReturn(Optional.empty());
//
//        listaCompraService.anadirRecetaAlCarrito(idUsuario, idReceta);
//
//        verify(listaCompraIngredienteRepository, times(1)).save(argThat(item ->
//                item.getCantidad().equals(2.0) && item.getIngrediente().getId().equals(10)
//        ));
//    }
//
//    @Test
//    @DisplayName("Test Unitario Negativo: No hace nada si la receta no tiene ingredientes")
//    public void testAnadirRecetaVaciaAlCarrito() {
//        when(listaCompraRepository.findByUsuarioId(1)).thenReturn(Optional.of(new ListaCompra()));
//        when(recetaIngredienteRepository.findByRecetaIdEagerly(anyInt())).thenReturn(List.of());
//
//        listaCompraService.anadirRecetaAlCarrito(1, 99);
//
//        verify(listaCompraIngredienteRepository, never()).save(any());
//    }
//}