package com.oscar.proyecto.controller;

import com.oscar.proyecto.entity.Usuario;
import com.oscar.proyecto.repository.UsuarioRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioRepository usuarioRepo;

    public UsuarioController(UsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    @GetMapping
    public List<Usuario> getUsuarios() {
        return usuarioRepo.findAll();
    }
}
