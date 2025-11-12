package com.oscar.proyecto.controller;

import com.oscar.proyecto.entity.Ingrediente;
import com.oscar.proyecto.repository.IngredienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/ingrediente")
public class IngredienteController {

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @GetMapping
    public ResponseEntity<List<Ingrediente>> getAllIngredientes() {
        List<Ingrediente> ingredientes = ingredienteRepository.findAll();
        return ResponseEntity.ok(ingredientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingrediente> getIngredienteById(@PathVariable Integer id) {
        Ingrediente ingrediente = ingredienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingrediente no encontrado"));
        return ResponseEntity.ok(ingrediente);
    }

    @PostMapping
    public ResponseEntity<Ingrediente> crearIngrediente(@RequestBody Ingrediente ingrediente) {
        Ingrediente nuevo = ingredienteRepository.save(ingrediente);
        return ResponseEntity.ok(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingrediente> actualizarIngrediente(@PathVariable Integer id, @RequestBody Ingrediente ingredienteActualizado) {
        Ingrediente ingrediente = ingredienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingrediente no encontrado"));

        ingrediente.setNombre(ingredienteActualizado.getNombre());
        ingrediente.setUnidadMedida(ingredienteActualizado.getUnidadMedida());
        ingrediente.setImagen_url(ingredienteActualizado.getImagen_url());

        Ingrediente actualizado = ingredienteRepository.save(ingrediente);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarIngrediente(@PathVariable Integer id) {
        if (!ingredienteRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("Ingrediente no existe");
        }
        ingredienteRepository.deleteById(id);
        return ResponseEntity.ok("Ingrediente eliminado");
    }
}
