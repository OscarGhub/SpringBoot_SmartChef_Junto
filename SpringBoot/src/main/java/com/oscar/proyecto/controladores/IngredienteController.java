package com.oscar.proyecto.controladores;

import com.oscar.proyecto.dto.Ingrediente.IngredienteResponseDTO;
import com.oscar.proyecto.dto.Ingrediente.TopIngredienteDTO;
import com.oscar.proyecto.dto.ListaCompra.ListaCompraIngredienteDTO;
import com.oscar.proyecto.mapper.IngredienteMapper;
import com.oscar.proyecto.servicios.IngredienteService;
import com.oscar.proyecto.servicios.ListaCompraService;
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
    private final ListaCompraService listaCompraService;

    @GetMapping
    public ResponseEntity<List<IngredienteResponseDTO>> getAllIngredientes() {
        return ResponseEntity.ok(service.getAllIngredientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredienteResponseDTO> getIngredienteById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getIngredienteById(id));
    }

    @GetMapping("/top5")
    public List<TopIngredienteDTO> getTop5() {
        return service.getTop5Ingredientes();
    }

    @GetMapping("/usuario/{idUsuario}/ingredientes")
    public ResponseEntity<List<ListaCompraIngredienteDTO>> getIngredientesPorUsuario(
            @PathVariable Integer idUsuario) {

        List<ListaCompraIngredienteDTO> ingredientes = listaCompraService.obtenerIngredientesPorUsuario(idUsuario);

        return ResponseEntity.ok(ingredientes);
    }

}
