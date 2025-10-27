package com.oscar.proyecto.controller;

import com.oscar.proyecto.entity.Usuario;
import com.oscar.proyecto.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    private final UsuarioRepository usuarioRepo;

    public UsuarioController(UsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    @GetMapping
    public List<Usuario> getUsuarios() {
        return usuarioRepo.findAll();
    }

    @PostMapping
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        return usuarioRepo.save(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(
            @PathVariable Integer id,
            @RequestBody Usuario usuarioActualizado) {

        return usuarioRepo.findById(id)
                .map(usuarioExistente -> {
                    usuarioExistente.setCorreo_electronico(usuarioActualizado.getCorreo_electronico());
                    usuarioExistente.setFecha_nacimiento(usuarioActualizado.getFecha_nacimiento());

                    Usuario usuarioGuardado = usuarioRepo.save(usuarioExistente);
                    return ResponseEntity.ok(usuarioGuardado);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
