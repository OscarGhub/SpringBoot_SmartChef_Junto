package com.oscar.proyecto.controladores;

import com.oscar.proyecto.servicios.InventarioIngredienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.oscar.proyecto.dto.InventarioIngrediente.InventarioIngredienteRequestDTO;
import com.oscar.proyecto.dto.InventarioIngrediente.InventarioIngredienteResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/api/inventario-ingrediente")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class InventarioIngredienteController {

    private final InventarioIngredienteService service;

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<InventarioIngredienteResponseDTO>> obtenerInventarioPorUsuario(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(service.obtenerInventarioPorUsuario(usuarioId));
    }

    @PostMapping
    public ResponseEntity<InventarioIngredienteResponseDTO> agregarIngrediente(@RequestBody InventarioIngredienteRequestDTO request) {
        return ResponseEntity.ok(service.agregarIngredienteInventario(request));
    }

    @DeleteMapping("/{idInventario}/{idIngrediente}")
    public ResponseEntity<String> eliminarIngrediente(@PathVariable Integer idInventario,
                                                      @PathVariable Integer idIngrediente) {
        service.eliminarIngredienteInventario(idInventario, idIngrediente);
        return ResponseEntity.ok("Ingrediente eliminado del inventario");
    }

}
