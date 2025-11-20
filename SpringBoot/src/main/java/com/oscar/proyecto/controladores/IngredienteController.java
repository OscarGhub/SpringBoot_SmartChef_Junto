package com.oscar.proyecto.controladores;

import com.oscar.proyecto.dto.Ingrediente.IngredienteResponseDTO;
import com.oscar.proyecto.servicios.IngredienteService;
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

}
