package com.oscar.proyecto.controller;

import com.oscar.proyecto.dto.Ingrediente.IngredienteRequestDTO;
import com.oscar.proyecto.dto.Ingrediente.IngredienteResponseDTO;
import com.oscar.proyecto.service.IngredienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingrediente")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class IngredienteController {

    private final IngredienteService service;

    @GetMapping
    public ResponseEntity<List<IngredienteResponseDTO>> getAllIngredientes() {
        return ResponseEntity.ok(service.getAllIngredientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredienteResponseDTO> getIngredienteById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getIngredienteById(id));
    }

    @PostMapping
    public ResponseEntity<IngredienteResponseDTO> crearIngrediente(@RequestBody IngredienteRequestDTO request) {
        return ResponseEntity.ok(service.crearIngrediente(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IngredienteResponseDTO> actualizarIngrediente(@PathVariable Integer id,
                                                                        @RequestBody IngredienteRequestDTO request) {
        return ResponseEntity.ok(service.actualizarIngrediente(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarIngrediente(@PathVariable Integer id) {
        service.eliminarIngrediente(id);
        return ResponseEntity.ok("Ingrediente eliminado");
    }
}
