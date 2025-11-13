package com.oscar.proyecto.controller;

import com.oscar.proyecto.entity.Inventario;
import com.oscar.proyecto.entity.InventarioIngrediente;
import com.oscar.proyecto.entity.InventarioIngredienteId;
import com.oscar.proyecto.entity.Ingrediente;
import com.oscar.proyecto.repository.InventarioIngredienteRepository;
import com.oscar.proyecto.repository.InventarioRepository;
import com.oscar.proyecto.repository.IngredienteRepository;
import com.oscar.proyecto.repository.UsuarioRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/inventario-ingrediente")
@CrossOrigin(origins = "http://localhost:4200")
public class InventarioIngredienteController {

    @Autowired
    private InventarioIngredienteRepository inventarioIngredienteRepository;

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<InventarioIngredienteResponse>> obtenerInventarioPorUsuario(@PathVariable Integer usuarioId) {
        List<InventarioIngrediente> lista = inventarioIngredienteRepository.findByInventarioUsuarioId(usuarioId);

        List<InventarioIngredienteResponse> response = lista.stream()
                .map(i -> new InventarioIngredienteResponse(
                        i.getId().getIdInventario(),
                        i.getId().getIdIngrediente(),
                        i.getInventario().getUsuario().getId(),
                        i.getIngrediente().getId(),
                        i.getIngrediente().getNombre(),
                        i.getIngrediente().getUnidadMedida(),
                        i.getIngrediente().getImagen_url(),
                        i.getCantidad()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<InventarioIngredienteResponse> agregarIngrediente(@RequestBody InventarioIngredienteRequest request) {
        Inventario inventario = inventarioRepository.findById(request.getIdInventario())
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));

        Ingrediente ingrediente = ingredienteRepository.findById(request.getIdIngrediente())
                .orElseThrow(() -> new RuntimeException("Ingrediente no encontrado"));

        InventarioIngredienteId id = new InventarioIngredienteId(inventario.getId(), ingrediente.getId());

        InventarioIngrediente inventarioIngrediente = new InventarioIngrediente();
        inventarioIngrediente.setId(id);
        inventarioIngrediente.setInventario(inventario);
        inventarioIngrediente.setIngrediente(ingrediente);
        inventarioIngrediente.setCantidad(request.getCantidad());

        InventarioIngrediente guardado = inventarioIngredienteRepository.save(inventarioIngrediente);

        InventarioIngredienteResponse response = new InventarioIngredienteResponse(
                guardado.getId().getIdInventario(),
                guardado.getId().getIdIngrediente(),
                inventario.getUsuario().getId(),
                ingrediente.getId(),
                ingrediente.getNombre(),
                ingrediente.getUnidadMedida(),
                ingrediente.getImagen_url(),
                guardado.getCantidad()
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{idInventario}/{idIngrediente}")
    public ResponseEntity<String> eliminarIngrediente(@PathVariable Integer idInventario, @PathVariable Integer idIngrediente) {
        InventarioIngredienteId id = new InventarioIngredienteId(idInventario, idIngrediente);

        if (!inventarioIngredienteRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("Ingrediente en inventario no existe");
        }

        inventarioIngredienteRepository.deleteById(id);
        return ResponseEntity.ok("Ingrediente eliminado del inventario");
    }

    public UsuarioRepository getUsuarioRepository() {
        return usuarioRepository;
    }

    public void setUsuarioRepository(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Getter
    @Setter
    public static class InventarioIngredienteRequest {
        private Integer idInventario;
        private Integer idIngrediente;
        private BigDecimal cantidad;
    }

    @Getter
    @Setter
    public static class InventarioIngredienteResponse {
        private Integer idInventario;
        private Integer idIngrediente;
        private Integer usuarioId;
        private Integer ingredienteId;
        private String nombreIngrediente;
        private String unidadMedida;
        private String imagenUrl;
        private BigDecimal cantidad;

        public InventarioIngredienteResponse(Integer idInventario, Integer idIngrediente, Integer usuarioId,
                                             Integer ingredienteId, String nombreIngrediente, String unidadMedida,
                                             String imagenUrl, BigDecimal cantidad) {
            this.idInventario = idInventario;
            this.idIngrediente = idIngrediente;
            this.usuarioId = usuarioId;
            this.ingredienteId = ingredienteId;
            this.nombreIngrediente = nombreIngrediente;
            this.unidadMedida = unidadMedida;
            this.imagenUrl = imagenUrl;
            this.cantidad = cantidad;
        }
    }
}
