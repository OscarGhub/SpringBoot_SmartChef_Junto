package com.oscar.proyecto.controladores;

import com.oscar.proyecto.dto.Receta.RecetaRequestDTO;
import com.oscar.proyecto.dto.Receta.RecetaResponseDTO;
import com.oscar.proyecto.servicios.RecetaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/receta")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class RecetaController {

    private final RecetaService service;

    @GetMapping
    public List<RecetaResponseDTO> getRecetas() {
        return service.getAllRecetas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecetaResponseDTO> getRecetaById(@PathVariable Integer id) {
        RecetaResponseDTO dto = service.getRecetaById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public RecetaResponseDTO crearReceta(@RequestBody RecetaRequestDTO dto) {
        return service.crearReceta(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReceta(@PathVariable Integer id) {
        service.eliminarReceta(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecetaResponseDTO> actualizarReceta(@PathVariable Integer id, @RequestBody RecetaRequestDTO dto) {
        RecetaResponseDTO actualizado = service.actualizarReceta(id, dto);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/guardar/{idUsuario}")
    public RecetaResponseDTO guardarReceta(@PathVariable Integer id, @PathVariable Integer idUsuario) {
        return service.guardarReceta(id, idUsuario);
    }

    @DeleteMapping("/{id}/guardar/{idUsuario}")
    public RecetaResponseDTO quitarRecetaGuardada(@PathVariable Integer id, @PathVariable Integer idUsuario) {
        return service.quitarRecetaGuardada(id, idUsuario);
    }

    @GetMapping("/{id}/ya-guardada/{idUsuario}")
    public boolean recetaYaGuardada(@PathVariable Integer id, @PathVariable Integer idUsuario) {
        return service.recetaYaGuardada(id, idUsuario);
    }

    @GetMapping("/guardadas/{idUsuario}")
    public List<RecetaResponseDTO> getRecetasGuardadasPorUsuario(@PathVariable Integer idUsuario) {
        return service.getRecetasGuardadasPorUsuario(idUsuario);
    }

    @PostMapping("/recetas/filtro")
    public List<RecetaResponseDTO> filtrarRecetas(@RequestBody(required = false) List<Integer> preferencias) {
        return service.filtrarRecetasPorPreferencias(preferencias);
    }

    @GetMapping("/receta-mas-guardada")
    public RecetaResponseDTO getRecetaMasGuardada() {
        return service.obtenerRecetaMasGuardada();
    }

    @GetMapping("/receta-mas-guardada-con-usuario")
    public ResponseEntity<RecetaResponseDTO> getRecetaMasGuardadaConUsuarios() {
        RecetaResponseDTO dto = service.obtenerRecetaMasGuardadaConUsuarios();
        if (dto == null) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(dto);
    }

}
