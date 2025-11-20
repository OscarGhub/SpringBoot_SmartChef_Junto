package com.oscar.proyecto.controladores;

import com.oscar.proyecto.dto.Preferencia.PreferenciaDTO;
import com.oscar.proyecto.servicios.PreferenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/preferencias")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class PreferenciaController {

    private final PreferenciaService service;

    @GetMapping
    public List<PreferenciaDTO> getPreferencias() {
        return service.getAllPreferencias();
    }
}
