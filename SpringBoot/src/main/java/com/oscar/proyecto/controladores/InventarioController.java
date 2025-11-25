package com.oscar.proyecto.controladores;

import com.oscar.proyecto.dto.Inventario.InventarioRequestDTO;
import com.oscar.proyecto.dto.Inventario.InventarioResponseDTO;
import com.oscar.proyecto.dto.InventarioIngrediente.InventarioIngredienteResponseDTO;
import com.oscar.proyecto.modelos.Inventario;
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

    @PostMapping
    public ResponseEntity<InventarioResponseDTO> crearInventarioParaUsuario(@RequestBody InventarioRequestDTO request) {
        Inventario inventario = service.crearInventarioParaUsuarioPorId(request.getUsuarioId());
        return ResponseEntity.ok(new InventarioResponseDTO(inventario.getId(), inventario.getUsuario().getId()));
    }

    @GetMapping("/base/usuario/{usuarioId}")
    public ResponseEntity<List<InventarioResponseDTO>> obtenerInventariosBasePorUsuario(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(service.obtenerInventariosPorUsuario(usuarioId));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<InventarioIngredienteResponseDTO>> obtenerInventarioDetalladoPorUsuario(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(service.obtenerIngredientesDeInventarioPorUsuario(usuarioId));
    }

    @PostMapping("/{idInventario}/ingredientes/{idIngrediente}")
    public ResponseEntity<InventarioIngredienteResponseDTO> agregarIngredienteAlInventario(
            @PathVariable Integer idInventario,
            @PathVariable Integer idIngrediente,
            @RequestParam BigDecimal cantidad) {

        InventarioIngredienteResponseDTO inventarioIngrediente = service.agregarIngredienteAlInventario(idInventario, idIngrediente, cantidad);

        return ResponseEntity.ok(inventarioIngrediente);
    }

    @DeleteMapping("/{idInventario}/ingredientes/{idIngrediente}")
    public ResponseEntity<String> eliminarIngredienteDelInventario(@PathVariable Integer idInventario, @PathVariable Integer idIngrediente) {
        service.eliminarIngredienteDelInventario(idInventario, idIngrediente);
        return ResponseEntity.ok("Ingrediente eliminado del inventario");
    }
}