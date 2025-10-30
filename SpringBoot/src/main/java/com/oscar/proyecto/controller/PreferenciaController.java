package com.oscar.proyecto.controller;

import com.oscar.proyecto.entity.Preferencia;
import com.oscar.proyecto.repository.PreferenciaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/preferencias")
public class PreferenciaController {

    private final PreferenciaRepository repo;

    public PreferenciaController(PreferenciaRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Preferencia> getPreferencias() {
        return repo.findAll();
    }
}
