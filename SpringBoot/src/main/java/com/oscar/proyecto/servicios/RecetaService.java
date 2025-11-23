package com.oscar.proyecto.servicios;

import com.oscar.proyecto.dto.Receta.RecetaResponseDTO;
import com.oscar.proyecto.dto.Receta.RecetaRequestDTO;
import com.oscar.proyecto.modelos.Receta;
import com.oscar.proyecto.modelos.RecetaGuardada;
import com.oscar.proyecto.modelos.RecetaGuardadaId;
import com.oscar.proyecto.modelos.Usuario;
import com.oscar.proyecto.repositorios.RecetaGuardadaRepository;
import com.oscar.proyecto.repositorios.RecetaRepository;
import com.oscar.proyecto.repositorios.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecetaService {

    private final RecetaRepository recetaRepo;
    private final RecetaGuardadaRepository recetaGuardadaRepo;
    private final UsuarioRepository usuarioRepo;

    public List<RecetaResponseDTO> getAllRecetas() {
        return recetaRepo.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public RecetaResponseDTO getRecetaById(Integer id) {
        return recetaRepo.findById(id)
                .map(this::mapToDTO)
                .orElse(null);
    }

    public RecetaResponseDTO crearReceta(RecetaRequestDTO dto) {
        Receta receta = new Receta();
        receta.setTitulo(dto.getTitulo());
        receta.setDescripcion(dto.getDescripcion());
        receta.setTutorial(dto.getTutorial());
        receta.setTiempoPreparacion(dto.getTiempoPreparacion());
        receta.setFotoUrl(dto.getFotoUrl());
        Receta guardada = recetaRepo.save(receta);
        return mapToDTO(guardada);
    }

    public RecetaResponseDTO actualizarReceta(Integer id, RecetaRequestDTO dto) {
        return recetaRepo.findById(id)
                .map(receta -> {
                    receta.setTitulo(dto.getTitulo());
                    receta.setDescripcion(dto.getDescripcion());
                    receta.setTutorial(dto.getTutorial());
                    receta.setTiempoPreparacion(dto.getTiempoPreparacion());
                    receta.setFotoUrl(dto.getFotoUrl());
                    return mapToDTO(recetaRepo.save(receta));
                })
                .orElse(null);
    }

    @Transactional
    public RecetaResponseDTO guardarReceta(Integer idReceta, Integer idUsuario) {
        Receta receta = recetaRepo.findById(idReceta).orElse(null);
        Usuario usuario = usuarioRepo.findById(idUsuario).orElse(null);
        if (receta == null || usuario == null) return null;

        RecetaGuardadaId rgId = new RecetaGuardadaId(idUsuario, idReceta);
        if (!recetaGuardadaRepo.existsById(rgId)) {
            RecetaGuardada rg = new RecetaGuardada();
            rg.setId(rgId);
            rg.setReceta(receta);
            rg.setUsuario(usuario);
            recetaGuardadaRepo.save(rg);
            recetaRepo.incrementarNumFavoritos(idReceta);
        }

        Receta updatedReceta = recetaRepo.findById(idReceta).orElse(receta);
        return mapToDTO(updatedReceta);
    }

    @Transactional
    public RecetaResponseDTO quitarRecetaGuardada(Integer idReceta, Integer idUsuario) {
        Receta receta = recetaRepo.findById(idReceta).orElse(null);
        Usuario usuario = usuarioRepo.findById(idUsuario).orElse(null);
        if (receta == null || usuario == null) return null;

        RecetaGuardadaId rgId = new RecetaGuardadaId(idUsuario, idReceta);
        if (recetaGuardadaRepo.existsById(rgId)) {
            recetaGuardadaRepo.deleteById(rgId);
            recetaRepo.decrementarNumFavoritos(idReceta);
        }

        Receta updatedReceta = recetaRepo.findById(idReceta).orElse(receta);
        return mapToDTO(updatedReceta);
    }

    public boolean recetaYaGuardada(Integer idReceta, Integer idUsuario) {
        return recetaGuardadaRepo.existsById(new RecetaGuardadaId(idUsuario, idReceta));
    }

    public List<RecetaResponseDTO> getRecetasGuardadasPorUsuario(Integer idUsuario) {
        return recetaGuardadaRepo.findAllByIdUsuario(idUsuario)
                .stream()
                .map(rg -> {
                    Receta r = rg.getReceta();
                    RecetaResponseDTO dto = mapToDTO(r);
                    dto.setGuardada(true);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<RecetaResponseDTO> filtrarRecetasPorPreferencias(List<Integer> preferencias) {
        List<Receta> recetas;
        if (preferencias == null || preferencias.isEmpty()) {
            recetas = recetaRepo.findAll();
        } else {
            recetas = recetaRepo.findByPreferenciasIn(preferencias);
        }
        return recetas.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private RecetaResponseDTO mapToDTO(Receta receta) {
        return new RecetaResponseDTO(
                receta.getId(),
                receta.getTitulo(),
                receta.getDescripcion(),
                receta.getTutorial(),
                receta.getTiempoPreparacion(),
                receta.getFotoUrl(),
                receta.getNumFavoritos(),
                false
        );
    }
}
