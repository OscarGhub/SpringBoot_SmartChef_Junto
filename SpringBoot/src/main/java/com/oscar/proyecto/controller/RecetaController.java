package com.oscar.proyecto.controller;

import com.oscar.proyecto.entity.Receta;
import com.oscar.proyecto.entity.RecetaGuardada;
import com.oscar.proyecto.entity.RecetaGuardadaId;
import com.oscar.proyecto.entity.Usuario;
import com.oscar.proyecto.repository.RecetaRepository;
import com.oscar.proyecto.repository.RecetaGuardadaRepository;
import com.oscar.proyecto.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return ResponseEntity.ok(recetaRepo.findAll());
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
    public ResponseEntity<Receta> actualizarReceta(@PathVariable Integer id, @RequestBody Receta recetaActualizada) {
        return recetaRepo.findById(id)
                .map(recetaExistente -> {
                    recetaExistente.setTitulo(recetaActualizada.getTitulo());
                    recetaExistente.setDescripcion(recetaActualizada.getDescripcion());
                    recetaExistente.setTutorial(recetaActualizada.getTutorial());
                    recetaExistente.setTiempo_preparacion(recetaActualizada.getTiempo_preparacion());
                    recetaExistente.setFoto_url(recetaActualizada.getFoto_url());
                    return ResponseEntity.ok(recetaRepo.save(recetaExistente));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/guardar/{idUsuario}")
    @Transactional
    public ResponseEntity<Receta> guardarReceta(@PathVariable Integer id, @PathVariable Integer idUsuario) {
        Receta receta = recetaRepo.findById(id).orElse(null);
        Usuario usuario = usuarioRepo.findById(idUsuario).orElse(null);
        if (receta == null || usuario == null) return ResponseEntity.notFound().build();

        RecetaGuardadaId rgId = new RecetaGuardadaId();
        rgId.setId_receta(id);
        rgId.setId_usuario(idUsuario);

        if (!recetaGuardadaRepo.existsById(rgId)) {
            RecetaGuardada rg = new RecetaGuardada();
            rg.setId(rgId);
            rg.setReceta(receta);
            rg.setUsuario(usuario);
            recetaGuardadaRepo.save(rg);

            recetaRepo.incrementarNumFavoritos(id);
            receta.setNumFavoritos((receta.getNumFavoritos() == null ? 0 : receta.getNumFavoritos() + 1));
        }

        return ResponseEntity.ok(receta);
    }

    @DeleteMapping("/{id}/guardar/{idUsuario}")
    @Transactional
    public ResponseEntity<Receta> quitarRecetaGuardada(@PathVariable Integer id, @PathVariable Integer idUsuario) {
        Receta receta = recetaRepo.findById(id).orElse(null);
        Usuario usuario = usuarioRepo.findById(idUsuario).orElse(null);
        if (receta == null || usuario == null) return ResponseEntity.notFound().build();

        RecetaGuardadaId rgId = new RecetaGuardadaId();
        rgId.setId_receta(id);
        rgId.setId_usuario(idUsuario);

        if (recetaGuardadaRepo.existsById(rgId)) {
            recetaGuardadaRepo.deleteById(rgId);

            recetaRepo.decrementarNumFavoritos(id);
            receta.setNumFavoritos(Math.max((receta.getNumFavoritos() == null ? 0 : receta.getNumFavoritos() - 1), 0));
        }

        return ResponseEntity.ok(receta);
    }

    @GetMapping("/{id}/ya-guardada/{idUsuario}")
    public ResponseEntity<Boolean> recetaYaGuardada(@PathVariable Integer id, @PathVariable Integer idUsuario) {
        boolean existe = recetaGuardadaRepo.existsById(new RecetaGuardadaId(id, idUsuario));
        return ResponseEntity.ok(existe);
    }

    @GetMapping("/guardadas/{idUsuario}")
    public ResponseEntity<List<Receta>> getRecetasGuardadasPorUsuario(@PathVariable Integer idUsuario) {
        Usuario usuario = usuarioRepo.findById(idUsuario).orElse(null);
        if (usuario == null) return ResponseEntity.notFound().build();

        List<Receta> recetasGuardadas = recetaGuardadaRepo.findAllByIdUsuario(idUsuario)
                .stream()
                .map(rg -> {
                    Receta r = rg.getReceta();
                    r.setGuardada(true);
                    return r;
                })
                .toList();

        return ResponseEntity.ok(recetasGuardadas);
    }

    @PostMapping("/recetas/filtro")
    public ResponseEntity<List<Receta>> filtrarRecetasPorPreferencias(@RequestBody(required = false) List<Integer> preferencias) {
        List<Receta> recetas;

        if (preferencias == null || preferencias.isEmpty()) {
            recetas = recetaRepo.findAll();
        } else {
            recetas = recetaRepo.findByPreferenciasIn(preferencias);
        }

        return ResponseEntity.ok(recetas);
    }

}
