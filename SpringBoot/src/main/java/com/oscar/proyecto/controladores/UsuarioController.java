package com.oscar.proyecto.controladores;

import com.oscar.proyecto.dto.Usuario.UsuarioDTO;
import com.oscar.proyecto.modelos.Usuario;
import com.oscar.proyecto.servicios.UsuarioService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Usuario> getUsuarios() {
        return usuarioService.getTodos();
    }

    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody UsuarioDTO dto) {
        Usuario guardado = usuarioService.crearUsuario(dto);
        return ResponseEntity.ok(guardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Integer id,
                                                     @RequestBody UsuarioDTO dto) {
        Usuario actualizado = usuarioService.actualizarUsuario(id, dto);
        return actualizado != null ? ResponseEntity.ok(actualizado)
                : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/foto")
    public ResponseEntity<Usuario> actualizarFoto(@PathVariable Integer id,
                                                  @RequestParam("foto") MultipartFile archivo) {
        try {
            Usuario actualizado = usuarioService.actualizarFoto(id, archivo);
            return actualizado != null ? ResponseEntity.ok(actualizado)
                    : ResponseEntity.notFound().build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{id}/foto")
    public ResponseEntity<byte[]> obtenerFoto(@PathVariable Integer id) throws IOException {
        byte[] foto = usuarioService.obtenerFoto(id);
        String mimeType = usuarioService.obtenerMimeType(id);

        if (foto == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, mimeType != null ? mimeType : MediaType.APPLICATION_OCTET_STREAM_VALUE)
                .body(foto);
    }

    @GetMapping("/correo/{correo}")
    public ResponseEntity<Usuario> obtenerPorCorreo(@PathVariable String correo) {
        return usuarioService.obtenerPorCorreo(correo)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/correo")
    public ResponseEntity<Usuario> actualizarCorreo(
            @PathVariable Integer id,
            @RequestBody Map<String, String> body) {

        String correo = body.get("correoElectronico");
        if (correo == null || correo.isBlank()) {
            return ResponseEntity.badRequest().body(null);
        }

        Usuario usuarioActualizado = usuarioService.actualizarCorreo(id, correo);
        return usuarioActualizado != null
                ? ResponseEntity.ok(usuarioActualizado)
                : ResponseEntity.notFound().build();
    }

}
