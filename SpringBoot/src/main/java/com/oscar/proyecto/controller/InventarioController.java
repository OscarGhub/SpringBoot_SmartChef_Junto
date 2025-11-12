package com.oscar.proyecto.controller;

import com.oscar.proyecto.entity.Inventario;
import com.oscar.proyecto.entity.Ingrediente;
import com.oscar.proyecto.entity.Usuario;
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
@RequestMapping("/api/inventario")
@CrossOrigin(origins = "http://localhost:4200")
public class InventarioController {

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<InventarioResponse>> obtenerInventarioPorUsuario(@PathVariable Integer usuarioId) {
        List<InventarioResponse> lista = inventarioRepository.findByUsuarioId(usuarioId).stream()
                .map(i -> new InventarioResponse(
                        i.getId(),
                        i.getUsuario().getId(),
                        i.getIngrediente().getId(),
                        i.getIngrediente().getNombre(),
                        i.getIngrediente().getUnidadMedida(),
                        i.getIngrediente().getImagen_url(),
                        i.getCantidad()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @PostMapping
    public ResponseEntity<InventarioResponse> agregarIngredienteInventario(@RequestBody PostInventarioRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Ingrediente ingrediente = ingredienteRepository.findById(request.getIngredienteId())
                .orElseThrow(() -> new RuntimeException("Ingrediente no encontrado"));

        Inventario inventario = new Inventario();
        inventario.setUsuario(usuario);
        inventario.setIngrediente(ingrediente);
        inventario.setCantidad(request.getCantidad());

        Inventario nuevo = inventarioRepository.save(inventario);

        InventarioResponse response = new InventarioResponse(
                nuevo.getId(),
                usuario.getId(),
                ingrediente.getId(),
                ingrediente.getNombre(),
                ingrediente.getUnidadMedida(),
                ingrediente.getImagen_url(),
                nuevo.getCantidad()
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarIngredienteInventario(@PathVariable Integer id) {
        if (!inventarioRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("Ingrediente en inventario no existe");
        }
        inventarioRepository.deleteById(id);
        return ResponseEntity.ok("Ingrediente eliminado del inventario");
    }

    @Getter
    @Setter
    public static class PostInventarioRequest {
        private Integer usuarioId;
        private Integer ingredienteId;
        private BigDecimal cantidad;
    }

    @Getter
    @Setter
    public static class InventarioResponse {
        private Integer idInventario;
        private Integer idUsuario;
        private Integer idIngrediente;
        private String nombreIngrediente;
        private String unidadMedida;
        private String imagenUrl;
        private BigDecimal cantidad;

        public InventarioResponse(Integer idInventario, Integer idUsuario, Integer idIngrediente,
                                  String nombreIngrediente, String unidadMedida, String imagenUrl, BigDecimal cantidad) {
            this.idInventario = idInventario;
            this.idUsuario = idUsuario;
            this.idIngrediente = idIngrediente;
            this.nombreIngrediente = nombreIngrediente;
            this.unidadMedida = unidadMedida;
            this.imagenUrl = imagenUrl;
            this.cantidad = cantidad;
        }
    }
}
