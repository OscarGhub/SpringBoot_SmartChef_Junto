package com.oscar.proyecto.controller;

import com.oscar.proyecto.dto.IngredientesConRecetaDTO;
import com.oscar.proyecto.entity.*;
import com.oscar.proyecto.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/carrito")
public class ListaCompraController {

    @Autowired
    private ListaCompraRepository listaCompraRepository;

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Autowired
    private ListaCompraIngredienteRepository listaCompraIngredienteRepository;

    @Autowired
    private RecetaIngredienteRepository recetaIngredienteRepository;

    @Autowired
    private RecetaRepository recetaRepository;

    @PostMapping("/listacompra")
    public ResponseEntity<ListaCompra> crearListaCompra(@RequestBody Map<String, Integer> body) {
        Integer idUsuario = body.get("id_usuario");
        if (idUsuario == null) {
            return ResponseEntity.badRequest().build();
        }

        ListaCompra listaCompra = new ListaCompra();
        Usuario usuario = new Usuario();
        usuario.setId(idUsuario);
        listaCompra.setUsuario(usuario);
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

    @GetMapping("/listacompra/{idLista}/ingredientes")
    public ResponseEntity<List<IngredientesConRecetaDTO>> obtenerIngredientes(@PathVariable Integer idLista) {
        ListaCompra listaCompra = listaCompraRepository.findById(idLista)
                .orElseThrow(() -> new RuntimeException("Lista de compra no encontrada"));

        List<ListaCompraIngrediente> ingredientes = listaCompraIngredienteRepository.findByListaCompra(listaCompra);

        List<Integer> idsIngredientes = ingredientes.stream()
                .map(li -> li.getIngrediente().getId())
                .collect(Collectors.toList());

        List<RecetaIngrediente> recetaIngredientes = recetaIngredienteRepository.findByIngredienteIdIn(idsIngredientes);

        List<IngredientesConRecetaDTO> dtoList = ingredientes.stream().map(li -> {
            RecetaIngrediente ri = recetaIngredientes.stream()
                    .filter(r -> r.getIngrediente().getId().equals(li.getIngrediente().getId()))
                    .findFirst()
                    .orElse(null);

            Integer idReceta = ri != null ? ri.getReceta().getId() : null;
            String nombreReceta = ri != null ? ri.getReceta().getTitulo() : null;

            return new IngredientesConRecetaDTO(
                    li.getIngrediente().getId(),
                    li.getIngrediente().getNombre(),
                    li.getCantidad(),
                    idReceta,
                    nombreReceta
            );
        }).collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }

    @PostMapping("/listacompra/usuario/{idUsuario}/receta/{idReceta}")
    public ResponseEntity<String> anadirRecetaAlCarrito(
            @PathVariable Integer idUsuario,
            @PathVariable Integer idReceta) {

        ListaCompra listaCompra = listaCompraRepository.findByUsuarioId(idUsuario)
                .orElseGet(() -> {
                    ListaCompra nuevaLista = new ListaCompra();
                    Usuario usuario = new Usuario();
                    usuario.setId(idUsuario);
                    nuevaLista.setUsuario(usuario);
                    nuevaLista.setFecha_creacion(LocalDate.now());
                    return listaCompraRepository.save(nuevaLista);
                });

        List<RecetaIngrediente> ingredientesReceta = recetaIngredienteRepository.findByRecetaId(idReceta);

        for (RecetaIngrediente ri : ingredientesReceta) {
            ListaCompraIngredienteId listaIngredienteId = new ListaCompraIngredienteId(
                    listaCompra.getId(),
                    ri.getIngrediente().getId()
            );

            ListaCompraIngrediente listaIngrediente = listaCompraIngredienteRepository.findById(listaIngredienteId)
                    .orElse(new ListaCompraIngrediente());

            listaIngrediente.setId(listaIngredienteId);
            listaIngrediente.setListaCompra(listaCompra);
            listaIngrediente.setIngrediente(ri.getIngrediente());
            listaIngrediente.setCantidad(ri.getCantidad());

            listaCompraIngredienteRepository.save(listaIngrediente);
        }

        return ResponseEntity.ok("Ingredientes de la receta añadidos al carrito");
    }

    @DeleteMapping("/listacompra/usuario/{idUsuario}/receta/{idReceta}")
    public ResponseEntity<String> eliminarRecetaDelCarrito(
            @PathVariable Integer idUsuario,
            @PathVariable Integer idReceta) {

        ListaCompra listaCompra = listaCompraRepository.findByUsuarioId(idUsuario)
                .orElseThrow(() -> new RuntimeException("Lista de compra no encontrada"));

        List<RecetaIngrediente> ingredientesReceta = recetaIngredienteRepository.findByRecetaId(idReceta);

        for (RecetaIngrediente ri : ingredientesReceta) {
            ListaCompraIngredienteId listaIngredienteId = new ListaCompraIngredienteId(
                    listaCompra.getId(),
                    ri.getIngrediente().getId()
            );

            if (listaCompraIngredienteRepository.existsById(listaIngredienteId)) {
                listaCompraIngredienteRepository.deleteById(listaIngredienteId);
            }
        }

        return ResponseEntity.ok("Ingredientes de la receta eliminados del carrito");
    }

}
