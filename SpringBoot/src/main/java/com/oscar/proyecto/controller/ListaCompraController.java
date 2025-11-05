package com.oscar.proyecto.controller;

import com.oscar.proyecto.entity.*;
import com.oscar.proyecto.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/carrito")
public class ListaCompraController {

    @Autowired
    private ListaCompraRepository listaCompraRepository;

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Autowired
    private ListaCompraIngredienteRepository listaCompraIngredienteRepository;

    @PostMapping("/listacompra")
    public ResponseEntity<ListaCompra> crearListaCompra(@RequestBody ListaCompra listaCompra) {
        listaCompra.setFecha_creacion(LocalDate.now());
        ListaCompra nuevaLista = listaCompraRepository.save(listaCompra);
        return ResponseEntity.ok(nuevaLista);
    }

    @PostMapping("/listacompra/{idLista}/ingrediente/{idIngrediente}")
    public ResponseEntity<String> anadirIngrediente(
            @PathVariable Integer idLista,
            @PathVariable Integer idIngrediente,
            @RequestParam(required = false) Double cantidad) {

        ListaCompra listaCompra = listaCompraRepository.findById(idLista)
                .orElseThrow(() -> new RuntimeException("Lista de compra no encontrada"));

        Ingrediente ingrediente = ingredienteRepository.findById(idIngrediente)
                .orElseThrow(() -> new RuntimeException("Ingrediente no encontrado"));

        ListaCompraIngredienteId id = new ListaCompraIngredienteId(idLista, idIngrediente);

        ListaCompraIngrediente listaIngrediente = listaCompraIngredienteRepository.findById(id)
                .orElse(new ListaCompraIngrediente());

        listaIngrediente.setId(id);
        listaIngrediente.setListaCompra(listaCompra);
        listaIngrediente.setIngrediente(ingrediente);
        listaIngrediente.setCantidad(cantidad != null ? cantidad : 1.0);

        listaCompraIngredienteRepository.save(listaIngrediente);

        return ResponseEntity.ok("Ingrediente añadido a la lista");
    }

    @DeleteMapping("/listacompra/{idLista}/ingrediente/{idIngrediente}")
    public ResponseEntity<String> eliminarIngrediente(
            @PathVariable Integer idLista,
            @PathVariable Integer idIngrediente) {

        ListaCompraIngredienteId id = new ListaCompraIngredienteId(idLista, idIngrediente);

        if (!listaCompraIngredienteRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("Ingrediente no existe en la lista");
        }

        listaCompraIngredienteRepository.deleteById(id);
        return ResponseEntity.ok("Ingrediente eliminado de la lista");
    }
}
