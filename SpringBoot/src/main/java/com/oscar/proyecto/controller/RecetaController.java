package com.oscar.proyecto.controller;

import com.oscar.proyecto.entity.Receta;
import com.oscar.proyecto.entity.RecetaGuardada;
import com.oscar.proyecto.entity.RecetaGuardadaId;
import com.oscar.proyecto.entity.Usuario;
import com.oscar.proyecto.repository.RecetaRepository;
import com.oscar.proyecto.repository.RecetaGuardadaRepository;
import com.oscar.proyecto.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/receta")
public class RecetaController {

    private final RecetaRepository recetaRepo;
    private final RecetaGuardadaRepository recetaGuardadaRepo;
    private final UsuarioRepository usuarioRepo;

    public RecetaController(RecetaRepository recetaRepo,
                            RecetaGuardadaRepository recetaGuardadaRepo,
                            UsuarioRepository usuarioRepo) {
        this.recetaRepo = recetaRepo;
        this.recetaGuardadaRepo = recetaGuardadaRepo;
        this.usuarioRepo = usuarioRepo;
    }

    @GetMapping
    public ResponseEntity<List<Receta>> getRecetas() {
        List<Receta> recetas = recetaRepo.findAll();
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(recetas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Receta> getRecetaById(@PathVariable Integer id) {
        return recetaRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Receta crearReceta(@RequestBody Receta receta) {
        return recetaRepo.save(receta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Receta> actualizarReceta(
            @PathVariable Integer id,
            @RequestBody Receta recetaActualizada) {

        return recetaRepo.findById(id)
                .map(recetaExistente -> {
                    recetaExistente.setTitulo(recetaActualizada.getTitulo());
                    recetaExistente.setDescripcion(recetaActualizada.getDescripcion());
                    recetaExistente.setTutorial(recetaActualizada.getTutorial());
                    recetaExistente.setTiempo_preparacion(recetaActualizada.getTiempo_preparacion());
                    recetaExistente.setFoto_url(recetaActualizada.getFoto_url());
                    recetaExistente.setNum_favoritos(recetaActualizada.getNum_favoritos());
                    return ResponseEntity.ok(recetaRepo.save(recetaExistente));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/filtrar")
    public List<Receta> filtrarPorPreferencias(@RequestParam(required = false) List<Integer> preferencias) {
        if (preferencias == null || preferencias.isEmpty()) {
            return recetaRepo.findAll();
        }
        return recetaRepo.findByPreferenciasIn(preferencias);
    }

    @GetMapping("/favoritos")
    public List<Receta> getRecetasConFavoritos() {
        return recetaRepo.findAllWithNumFavoritos().stream()
                .map(obj -> {
                    Receta receta = (Receta) obj[0];
                    receta.setNum_favoritos((Long) obj[1]);
                    return receta;
                })
                .collect(Collectors.toList());
    }

    @PostMapping("/{id}/guardar/{idUsuario}")
    public ResponseEntity<Receta> guardarReceta(@PathVariable Integer id, @PathVariable Integer idUsuario) {
        Receta receta = recetaRepo.findById(id).orElse(null);
        if (receta == null) return ResponseEntity.notFound().build();

        Usuario usuario = usuarioRepo.findById(idUsuario).orElse(null);
        if (usuario == null) return ResponseEntity.notFound().build();

        RecetaGuardadaId rgId = new RecetaGuardadaId();
        rgId.setId_receta(id);
        rgId.setId_usuario(idUsuario);

        boolean yaGuardado = recetaGuardadaRepo.existsById(rgId);
        if (!yaGuardado) {
            RecetaGuardada rg = new RecetaGuardada();
            rg.setId(rgId);
            rg.setReceta(receta);
            rg.setUsuario(usuario);
            recetaGuardadaRepo.save(rg);

            receta.setNum_favoritos((receta.getNum_favoritos() == null ? 0 : receta.getNum_favoritos()) + 1);
            recetaRepo.save(receta);
        }

        return ResponseEntity.ok(receta);
    }

    @DeleteMapping("/{id}/guardar/{idUsuario}")
    public ResponseEntity<Receta> quitarRecetaGuardada(@PathVariable Integer id, @PathVariable Integer idUsuario) {
        Receta receta = recetaRepo.findById(id).orElse(null);
        if (receta == null) return ResponseEntity.notFound().build();

        Usuario usuario = usuarioRepo.findById(idUsuario).orElse(null);
        if (usuario == null) return ResponseEntity.notFound().build();

        RecetaGuardadaId rgId = new RecetaGuardadaId();
        rgId.setId_receta(id);
        rgId.setId_usuario(idUsuario);

        if (recetaGuardadaRepo.existsById(rgId)) {
            recetaGuardadaRepo.deleteById(rgId);

            receta.setNum_favoritos((receta.getNum_favoritos() == null ? 0 : receta.getNum_favoritos()) - 1);
            recetaRepo.save(receta);
        }

        return ResponseEntity.ok(receta);
    }

    @GetMapping("/{id}/ya-guardada/{idUsuario}")
    public ResponseEntity<Boolean> recetaYaGuardada(@PathVariable Integer id, @PathVariable Integer idUsuario) {
        RecetaGuardadaId rgId = new RecetaGuardadaId();
        rgId.setId_receta(id);
        rgId.setId_usuario(idUsuario);

        boolean existe = recetaGuardadaRepo.existsById(rgId);
        return ResponseEntity.ok(existe);
    }

}
