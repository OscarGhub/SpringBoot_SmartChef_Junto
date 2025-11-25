package com.oscar.proyecto.controladores;

import com.oscar.proyecto.dto.Receta.RecetaUsoDTO;
import com.oscar.proyecto.servicios.RecetaCocinadaFechaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
