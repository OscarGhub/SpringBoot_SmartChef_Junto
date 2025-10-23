package com.oscar.proyecto.controller;

import com.oscar.proyecto.entity.Receta;
import com.oscar.proyecto.repository.RecetaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/receta")
@CrossOrigin(origins = {"http://localhost:4200"})
public class RecetaController {

    private final RecetaRepository recetaRepo;

    public RecetaController(RecetaRepository recetaRepo) {
        this.recetaRepo = recetaRepo;
    }

    @GetMapping
    public List<Receta> getRecetas() {
        return recetaRepo.findAll();
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
                    recetaExistente.setTiempoPreparacion(recetaActualizada.getTiempoPreparacion());
                    recetaExistente.setFotoUrl(recetaActualizada.getFotoUrl());
                    recetaExistente.setNumFavoritos(recetaActualizada.getNumFavoritos());

                    Receta recetaGuardada = recetaRepo.save(recetaExistente);
                    return ResponseEntity.ok(recetaGuardada);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
