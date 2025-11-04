package com.oscar.proyecto.controller;

import com.oscar.proyecto.entity.ListaCompra;
import com.oscar.proyecto.entity.Receta;
import com.oscar.proyecto.repository.ListaCompraRepository;
import com.oscar.proyecto.repository.RecetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class ListaCompraController {

    @Autowired
    private ListaCompraRepository listaCompraRepository;

    @Autowired
    private RecetaRepository recetaRepository;

    @PostMapping("/api/carrito/listacompra")
    public ListaCompra crearListaCompra(@RequestBody ListaCompra listaCompra) {
        ListaCompra carrito = new ListaCompra();
        carrito.setId_usuario(listaCompra.getId_usuario());
        carrito.setFecha_creacion(LocalDate.now());
        return listaCompraRepository.save(carrito);
    }

    @PostMapping("/api/carrito/listacompra/{idLista}/receta")
    public Receta anadirRecetaAlCarrito(@PathVariable Integer idLista, @RequestBody Receta receta) {
        ListaCompra listaCompra = listaCompraRepository.findById(idLista)
                .orElseThrow(() -> new RuntimeException("Lista de compra no encontrada"));

        receta.setIdLista(idLista);

        recetaRepository.save(receta);

        return receta;
    }
}
