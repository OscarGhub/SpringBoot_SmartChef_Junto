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
import com.oscar.proyecto.mapper.RecetaMapper;
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
    private final RecetaMapper recetaMapper;

    private RecetaResponseDTO mapRecetaConFavoritos(Receta receta) {
        RecetaResponseDTO dto = recetaMapper.toResponseDTO(receta);
        int numFav = recetaGuardadaRepo.contarGuardados(receta.getId());
        dto.setNumFavoritos(numFav);
        return dto;
    }

    public List<RecetaResponseDTO> getAllRecetas() {
        return recetaMapper.toResponseDTOList(recetaRepo.findAll());
    }

    public RecetaResponseDTO getRecetaById(Integer id) {
        return recetaRepo.findById(id)
                .map(this::mapRecetaConFavoritos)
                .orElse(null);
    }

    public RecetaResponseDTO crearReceta(RecetaRequestDTO dto) {
        if (dto.getTitulo() == null || dto.getTitulo().isBlank()) {
            throw new IllegalArgumentException("El título es obligatorio");
        }
        Receta receta = recetaMapper.toEntity(dto);
        Receta guardada = recetaRepo.save(receta);
        return mapRecetaConFavoritos(guardada);
    }

    @Transactional
    public void eliminarReceta(Integer id) {
        if (!recetaRepo.existsById(id)) {
            System.out.println("Receta con ID " + id + " no encontrada para eliminar.");
            return;
        }

        recetaRepo.deleteById(id);
    }

    public RecetaResponseDTO actualizarReceta(Integer id, RecetaRequestDTO dto) {
        return recetaRepo.findById(id)
                .map(receta -> {
                    recetaMapper.updateEntityFromDto(dto, receta);
                    Receta actualizada = recetaRepo.save(receta);
                    return mapRecetaConFavoritos(actualizada);
                })
                .orElse(null);
    }

    @Transactional
    public RecetaResponseDTO guardarReceta(Integer idReceta, Integer idUsuario) {

        Receta receta = recetaRepo.findById(idReceta)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada"));

        Usuario usuario = usuarioRepo.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        RecetaGuardadaId rgId = new RecetaGuardadaId(idUsuario, idReceta);

        if (!recetaGuardadaRepo.existsById(rgId)) {
            RecetaGuardada rg = new RecetaGuardada();
            rg.setId(rgId);
            rg.setReceta(receta);
            rg.setUsuario(usuario);
            recetaGuardadaRepo.save(rg);
        }

        RecetaResponseDTO dto = mapRecetaConFavoritos(receta);
        dto.setGuardada(true);

        return dto;
    }

    @Transactional
    public RecetaResponseDTO quitarRecetaGuardada(Integer idReceta, Integer idUsuario) {

        Receta receta = recetaRepo.findById(idReceta).orElse(null);
        Usuario usuario = usuarioRepo.findById(idUsuario).orElse(null);
        if (receta == null || usuario == null) return null;

        RecetaGuardadaId rgId = new RecetaGuardadaId(idUsuario, idReceta);

        if (recetaGuardadaRepo.existsById(rgId)) {
            recetaGuardadaRepo.deleteById(rgId);
        }

        return mapRecetaConFavoritos(receta);
    }

    public boolean recetaYaGuardada(Integer idReceta, Integer idUsuario) {
        return recetaGuardadaRepo.existsById(new RecetaGuardadaId(idUsuario, idReceta));
    }

    public List<RecetaResponseDTO> getRecetasGuardadasPorUsuario(Integer idUsuario) {
        return recetaGuardadaRepo.findAllByIdUsuario(idUsuario)
                .stream()
                .map(rg -> {
                    RecetaResponseDTO dto = mapRecetaConFavoritos(rg.getReceta());
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

        return recetas.stream()
                .map(this::mapRecetaConFavoritos)
                .collect(Collectors.toList());
    }

    public RecetaResponseDTO obtenerRecetaMasGuardada() {
        Integer idReceta = recetaGuardadaRepo.findRecetaMasGuardada();
        if (idReceta == null) return null;

        Receta receta = recetaRepo.findById(idReceta).orElse(null);
        if (receta == null) return null;

        RecetaResponseDTO dto = mapRecetaConFavoritos(receta);

        List<Usuario> usuarios = recetaGuardadaRepo.findUsuariosPorReceta(idReceta);
        dto.setUsuariosQueGuardaron(usuarios);

        return dto;
    }

    public RecetaResponseDTO obtenerRecetaMasGuardadaConUsuarios() {
        Integer idRecetaMasGuardada = recetaGuardadaRepo.findRecetaMasGuardada();
        if (idRecetaMasGuardada == null) return null;

        Receta receta = recetaRepo.findById(idRecetaMasGuardada).orElse(null);
        if (receta == null) return null;

        RecetaResponseDTO dto = mapRecetaConFavoritos(receta);

        List<Usuario> usuarios = recetaGuardadaRepo.findUsuariosPorReceta(idRecetaMasGuardada);
        dto.setUsuariosQueGuardaron(usuarios);

        return dto;
    }

}
