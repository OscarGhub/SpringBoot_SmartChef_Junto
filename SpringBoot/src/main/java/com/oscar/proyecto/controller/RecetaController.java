package com.oscar.proyecto.controller;

import com.oscar.proyecto.entity.Receta;
import com.oscar.proyecto.repository.RecetaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/receta")
public class RecetaController {

    private final RecetaRepository recetaRepo;

    public RecetaController(RecetaRepository recetaRepo) {
        this.recetaRepo = recetaRepo;
    }

    @GetMapping
    public List<Receta> getRecetas() {
        return recetaRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Receta> getRecetaById(@PathVariable Integer id) {
        return recetaRepo.findById(id)
                .map(receta -> ResponseEntity.ok(receta))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Receta crearReceta(@RequestBody Receta receta) {
        return recetaRepo.save(receta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Receta> actualizarReceta(
            @PathVariable Integer id,
            @RequestBody Receta recetaActualizada) {

        return recetaRepo.findById(id)
                .map(recetaExistente -> {
                    recetaExistente.setTitulo(recetaActualizada.getTitulo());
                    recetaExistente.setDescripcion(recetaActualizada.getDescripcion());
                    recetaExistente.setTiempo_preparacion(recetaActualizada.getTiempo_preparacion());
                    recetaExistente.setFoto_url(recetaActualizada.getFoto_url());
                    recetaExistente.setNum_favoritos(recetaActualizada.getNum_favoritos());

                    Receta recetaGuardada = recetaRepo.save(recetaExistente);
                    return ResponseEntity.ok(recetaGuardada);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
