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

    public List<RecetaResponseDTO> getAllRecetas() {
        return recetaMapper.toResponseDTOList(recetaRepo.findAll());
    }

    public RecetaResponseDTO getRecetaById(Integer id) {
        return recetaRepo.findById(id)
                .map(recetaMapper::toResponseDTO)
                .orElse(null);
    }

    public RecetaResponseDTO crearReceta(RecetaRequestDTO dto) {
        Receta receta = recetaMapper.toEntity(dto);
        Receta guardada = recetaRepo.save(receta);
        return recetaMapper.toResponseDTO(guardada);
    }

    public RecetaResponseDTO actualizarReceta(Integer id, RecetaRequestDTO dto) {
        return recetaRepo.findById(id)
                .map(receta -> {
                    recetaMapper.updateEntityFromDto(dto, receta);
                    return recetaMapper.toResponseDTO(recetaRepo.save(receta));
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
        RecetaResponseDTO dto = recetaMapper.toResponseDTO(updatedReceta);
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
            recetaRepo.decrementarNumFavoritos(idReceta);
        }

        Receta updatedReceta = recetaRepo.findById(idReceta).orElse(receta);
        return recetaMapper.toResponseDTO(updatedReceta);
    }

    public boolean recetaYaGuardada(Integer idReceta, Integer idUsuario) {
        return recetaGuardadaRepo.existsById(new RecetaGuardadaId(idUsuario, idReceta));
    }

    public List<RecetaResponseDTO> getRecetasGuardadasPorUsuario(Integer idUsuario) {
        return recetaGuardadaRepo.findAllByIdUsuario(idUsuario)
                .stream()
                .map(rg -> {
                    Receta r = rg.getReceta();
                    RecetaResponseDTO dto = recetaMapper.toResponseDTO(r);
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
        return recetaMapper.toResponseDTOList(recetas);
    }

    public RecetaResponseDTO obtenerRecetaMasGuardada() {

        Integer idReceta = recetaGuardadaRepo.findRecetaMasGuardada();
        if (idReceta == null) {
            return null;
        }

        Receta receta = recetaRepo.findById(idReceta).orElse(null);
        if (receta == null) {
            return null;
        }

        RecetaResponseDTO dto = recetaMapper.toResponseDTO(receta);

        List<Usuario> usuarios = recetaGuardadaRepo.findUsuariosPorReceta(idReceta);
        dto.setUsuariosQueGuardaron(usuarios);

        return dto;
    }

    public RecetaResponseDTO obtenerRecetaMasGuardadaConUsuarios() {
        Integer idRecetaMasGuardada = recetaGuardadaRepo.findRecetaMasGuardada();
        if (idRecetaMasGuardada == null) return null;

        Receta receta = recetaRepo.findById(idRecetaMasGuardada).orElse(null);
        if (receta == null) return null;

        RecetaResponseDTO dto = recetaMapper.toResponseDTO(receta);

        List<Usuario> usuarios = recetaGuardadaRepo.findUsuariosPorReceta(idRecetaMasGuardada);
        dto.setUsuariosQueGuardaron(usuarios);

        return dto;
    }

}