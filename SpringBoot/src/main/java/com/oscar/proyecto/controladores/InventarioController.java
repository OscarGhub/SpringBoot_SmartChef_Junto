package com.oscar.proyecto.controladores;

import com.oscar.proyecto.dto.Inventario.InventarioResponseDTO;
import com.oscar.proyecto.modelos.Inventario;
import com.oscar.proyecto.modelos.InventarioIngrediente;
import com.oscar.proyecto.servicios.InventarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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

    @PostMapping("/{idInventario}/ingredientes/{idIngrediente}")
    public ResponseEntity<InventarioIngrediente> agregarIngredienteAlInventario(
            @PathVariable Integer idInventario,
            @PathVariable Integer idIngrediente,
            @RequestParam BigDecimal cantidad) {

        InventarioIngrediente inventarioIngrediente = service.agregarIngredienteAlInventario(idInventario, idIngrediente, cantidad);

        return ResponseEntity.ok(inventarioIngrediente);
    }

}
