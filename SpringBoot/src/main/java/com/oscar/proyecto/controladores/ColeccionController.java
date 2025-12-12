package com.oscar.proyecto.controladores;

import com.oscar.proyecto.dto.Coleccion.ColeccionRequestDTO;
import com.oscar.proyecto.dto.Coleccion.ColeccionResponseDTO;
import com.oscar.proyecto.servicios.ColeccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/colecciones")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ColeccionController {

    private final ColeccionService service;

    @PostMapping("/crear")
    public ResponseEntity<ColeccionResponseDTO> crearColeccion(@RequestBody ColeccionRequestDTO request) {
        ColeccionResponseDTO nuevaColeccion = service.crearColeccion(request);
        return ResponseEntity.ok(nuevaColeccion);
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<ColeccionResponseDTO>> obtenerColeccionesPorUsuario(@PathVariable Integer idUsuario) {
        List<ColeccionResponseDTO> colecciones = service.obtenerColeccionesPorUsuario(idUsuario);
        return ResponseEntity.ok(colecciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ColeccionResponseDTO> obtenerColeccionPorId(@PathVariable Integer id) {
        ColeccionResponseDTO coleccion = service.obtenerColeccionPorId(id);
        return coleccion != null ? ResponseEntity.ok(coleccion) : ResponseEntity.notFound().build();
    }
}