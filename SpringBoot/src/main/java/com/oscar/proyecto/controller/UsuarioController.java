package com.oscar.proyecto.controller;

import com.oscar.proyecto.entity.Usuario;
import com.oscar.proyecto.repository.UsuarioRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    private final UsuarioRepository usuarioRepo;
    private final String CARPETA_UPLOADS = "C:/proyecto/uploads/";

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
                    usuarioExistente.setCorreoElectronico(usuarioActualizado.getCorreoElectronico());
                    usuarioExistente.setFechaNacimiento(usuarioActualizado.getFechaNacimiento());
                    Usuario usuarioGuardado = usuarioRepo.save(usuarioExistente);
                    return ResponseEntity.ok(usuarioGuardado);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/foto")
    public ResponseEntity<Usuario> actualizarFoto(
            @PathVariable Integer id,
            @RequestParam("foto") MultipartFile archivo) {

        Usuario usuario = usuarioRepo.findById(id).orElse(null);
        if (usuario == null) return ResponseEntity.notFound().build();

        try {
            Files.createDirectories(Paths.get(CARPETA_UPLOADS));

            String nombreArchivo = "usuario_" + id + "_" + System.currentTimeMillis() + "_"
                    + archivo.getOriginalFilename().replaceAll("\\s+", "_");
            File destino = new File(CARPETA_UPLOADS + nombreArchivo);
            archivo.transferTo(destino);

            usuario.setFotoUrl("/uploads/" + nombreArchivo);
            Usuario usuarioActualizado = usuarioRepo.save(usuario);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{id}/foto")
    public ResponseEntity<byte[]> obtenerFoto(@PathVariable Integer id) throws IOException {
        Usuario usuario = usuarioRepo.findById(id).orElse(null);
        if (usuario == null || usuario.getFotoUrl() == null) return ResponseEntity.notFound().build();

        String path = usuario.getFotoUrl();
        if (path.startsWith("/uploads/")) {
            path = path.replaceFirst("/uploads/", "");
        }

        File archivo = new File(CARPETA_UPLOADS + path);

        if (!archivo.exists()) return ResponseEntity.notFound().build();

        byte[] bytes = Files.readAllBytes(archivo.toPath());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                .body(bytes);
    }

    @GetMapping("/correo/{correo}")
    public ResponseEntity<Usuario> obtenerPorCorreo(@PathVariable String correo) {
        try {
            String correoDecodificado = URLDecoder.decode(correo, StandardCharsets.UTF_8);
            Optional<Usuario> usuarioOpt = usuarioRepo.findByCorreoElectronico(correoDecodificado);

            if (usuarioOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(usuarioOpt.get());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

}
