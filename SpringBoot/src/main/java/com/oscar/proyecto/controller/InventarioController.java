package com.oscar.proyecto.controller;

import com.oscar.proyecto.entity.Inventario;
import com.oscar.proyecto.entity.Usuario;
import com.oscar.proyecto.repository.InventarioRepository;
import com.oscar.proyecto.repository.UsuarioRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/inventario")
@CrossOrigin(origins = "http://localhost:4200")
public class InventarioController {

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<InventarioResponse> crearInventario(@RequestBody InventarioRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Inventario inventario = new Inventario();
        inventario.setId(request.getIdInventario());
        inventario.setUsuario(usuario);

        Inventario guardado = inventarioRepository.save(inventario);

        InventarioResponse response = new InventarioResponse(
                guardado.getId(),
                usuario.getId()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<InventarioResponse>> obtenerInventariosPorUsuario(@PathVariable Integer usuarioId) {
        List<Inventario> inventarios = inventarioRepository.findByUsuarioId(usuarioId);

        List<InventarioResponse> response = inventarios.stream()
                .map(i -> new InventarioResponse(i.getId(), i.getUsuario().getId()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @Getter
    @Setter
    public static class InventarioRequest {
        private Integer idInventario;
        private Integer usuarioId;
    }

    @Getter
    @Setter
    public static class InventarioResponse {
        private Integer idInventario;
        private Integer usuarioId;

        public InventarioResponse(Integer idInventario, Integer usuarioId) {
            this.idInventario = idInventario;
            this.usuarioId = usuarioId;
        }
    }
}
