package com.oscar.proyecto.servicios;

import com.oscar.proyecto.dto.Coleccion.ColeccionRequestDTO;
import com.oscar.proyecto.dto.Coleccion.ColeccionResponseDTO;
import com.oscar.proyecto.exception.ElementoNoEncontradoException;
import com.oscar.proyecto.mapper.ColeccionMapper;
import com.oscar.proyecto.modelos.Coleccion;
import com.oscar.proyecto.modelos.Usuario;
import com.oscar.proyecto.repositorios.ColeccionRepository;
import com.oscar.proyecto.repositorios.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ColeccionService {

    private final ColeccionRepository coleccionRepository;
    private final UsuarioRepository usuarioRepository;
    private final ColeccionMapper coleccionMapper;

    @Transactional
    public ColeccionResponseDTO crearColeccion(ColeccionRequestDTO request) {
        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new ElementoNoEncontradoException("Usuario no encontrado con ID: " + request.getIdUsuario()));

        Coleccion nuevaColeccion = new Coleccion();
        nuevaColeccion.setNombre(request.getNombre());
        nuevaColeccion.setUsuario(usuario);

        Coleccion coleccionGuardada = coleccionRepository.save(nuevaColeccion);

        return coleccionMapper.toResponseDTO(coleccionGuardada);
    }

    @Transactional(readOnly = true)
    public List<ColeccionResponseDTO> obtenerColeccionesPorUsuario(Integer idUsuario) {
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new ElementoNoEncontradoException("Usuario no encontrado con ID: " + idUsuario);
        }

        List<Coleccion> colecciones = coleccionRepository.findByUsuarioId(idUsuario);

        return coleccionMapper.toResponseDTOList(colecciones);
    }

    @Transactional(readOnly = true)
    public ColeccionResponseDTO obtenerColeccionPorId(Integer id) {
        Optional<Coleccion> coleccion = coleccionRepository.findById(id);

        return coleccion.map(coleccionMapper::toResponseDTO)
                .orElse(null);
    }
}