package com.oscar.proyecto.controller;

import com.oscar.proyecto.entity.ListaCompra;
import com.oscar.proyecto.entity.Receta;
import com.oscar.proyecto.repository.ListaCompraRepository;
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

    @PostMapping("/api/carrito/listacompra")
    public ListaCompra crearListaCompra(@RequestBody ListaCompra listaCompra) {
        ListaCompra carrito = new ListaCompra(listaCompra.getId_usuario(), LocalDate.now());
        return listaCompraRepository.save(carrito);
    }

    @PostMapping("/api/carrito/listacompra/{idLista}/receta")
    public Receta anadirRecetaAlCarrito(@PathVariable Long idLista, @RequestBody Receta receta) {
        receta.setIdLista(idLista);

        return receta;
    }
}
