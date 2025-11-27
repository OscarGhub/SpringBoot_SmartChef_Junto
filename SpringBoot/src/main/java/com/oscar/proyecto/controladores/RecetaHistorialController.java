package com.oscar.proyecto.controladores;

import com.oscar.proyecto.dto.Receta.RecetaUsoDTO;
import com.oscar.proyecto.dto.Receta.RecetaUsoRequestDTO;
import com.oscar.proyecto.servicios.RecetaCocinadaFechaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historial")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class RecetaHistorialController {

    private final RecetaCocinadaFechaService recetaCocinadaFechaService;

    @GetMapping("/recetas-semanal")
    public List<RecetaUsoDTO> getRecetasSemana() {
        return recetaCocinadaFechaService.getRecetasUltimaSemana();
    }

    @PostMapping("/receta-dia")
    public RecetaUsoDTO guardarRecetaDia(@RequestBody RecetaUsoRequestDTO dto) {
        return recetaCocinadaFechaService.guardarRecetaEnFecha(dto);
    }
}
