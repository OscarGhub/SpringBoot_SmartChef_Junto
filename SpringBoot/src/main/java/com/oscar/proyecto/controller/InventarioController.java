package com.oscar.proyecto.controller;

import com.oscar.proyecto.dto.Inventario.InventarioRequestDTO;
import com.oscar.proyecto.dto.Inventario.InventarioResponseDTO;
import com.oscar.proyecto.entity.Inventario;
import com.oscar.proyecto.service.InventarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class InventarioController {

    private final InventarioService service;

    @PostMapping("/usuario/{usuarioId}")
    public ResponseEntity<InventarioResponseDTO> crearInventarioParaUsuario(@PathVariable Integer usuarioId) {
        Inventario inventario = service.crearInventarioParaUsuarioPorId(usuarioId);
        return ResponseEntity.ok(new InventarioResponseDTO(inventario.getId(), inventario.getUsuario().getId()));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<InventarioResponseDTO>> obtenerInventariosPorUsuario(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(service.obtenerInventariosPorUsuario(usuarioId));
    }
}
