package com.oscar.proyecto.controladores;

import com.oscar.proyecto.dto.ListaCompra.ListaCompraRequestDTO;
import com.oscar.proyecto.dto.ListaCompra.ListaCompraResponseDTO;
import com.oscar.proyecto.dto.ListaCompra.ListaCompraIngredienteDTO;
import com.oscar.proyecto.servicios.ListaCompraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carrito")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class ListaCompraController {

    private final ListaCompraService service;

    @PostMapping
    public ResponseEntity<ListaCompraResponseDTO> crearListaCompra(@RequestBody ListaCompraRequestDTO request) {
        return ResponseEntity.ok(service.crearListaCompra(request));
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<ListaCompraResponseDTO>> obtenerListasPorUsuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(service.obtenerListasPorUsuario(idUsuario));
    }

    @PostMapping("/{idLista}/ingrediente")
    public ResponseEntity<ListaCompraIngredienteDTO> anadirIngrediente(@PathVariable Integer idLista,
                                                                       @RequestBody ListaCompraIngredienteDTO request) {
        return ResponseEntity.ok(service.anadirIngrediente(idLista, request));
    }

    @DeleteMapping("/{idLista}/ingrediente/{idIngrediente}")
    public ResponseEntity<String> eliminarIngrediente(@PathVariable Integer idLista,
                                                      @PathVariable Integer idIngrediente) {
        service.eliminarIngrediente(idLista, idIngrediente);
        return ResponseEntity.ok("Ingrediente eliminado de la lista");
    }

    @GetMapping("/{idLista}/ingredientes")
    public ResponseEntity<List<ListaCompraIngredienteDTO>> obtenerIngredientes(@PathVariable Integer idLista) {
        return ResponseEntity.ok(service.obtenerIngredientes(idLista));
    }

    @PostMapping("/usuario/{idUsuario}/receta/{idReceta}")
    public ResponseEntity<String> anadirRecetaAlCarrito(@PathVariable Integer idUsuario,
                                                        @PathVariable Integer idReceta) {
        service.anadirRecetaAlCarrito(idUsuario, idReceta);
        return ResponseEntity.ok("Ingredientes de la receta añadidos al carrito");
    }

    @DeleteMapping("/usuario/{idUsuario}/receta/{idReceta}")
    public ResponseEntity<String> eliminarRecetaDelCarrito(@PathVariable Integer idUsuario,
                                                           @PathVariable Integer idReceta) {
        service.eliminarRecetaDelCarrito(idUsuario, idReceta);
        return ResponseEntity.ok("Ingredientes de la receta eliminados del carrito");
    }

    @GetMapping("/usuario/{idUsuario}/receta/{idReceta}/existe")
    public ResponseEntity<Boolean> recetaEstaEnCarrito(@PathVariable Integer idUsuario,
                                                       @PathVariable Integer idReceta) {
        return ResponseEntity.ok(service.recetaEstaEnCarrito(idUsuario, idReceta));
    }
}
