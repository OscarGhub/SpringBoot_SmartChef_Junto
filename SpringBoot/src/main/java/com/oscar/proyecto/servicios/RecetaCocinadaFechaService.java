package com.oscar.proyecto.servicios;

import com.oscar.proyecto.dto.Receta.RecetaUsoDTO;
import com.oscar.proyecto.dto.Receta.RecetaUsoRequestDTO;
import com.oscar.proyecto.exception.ElementoNoEncontradoException;
import com.oscar.proyecto.modelos.RecetaCocinadaFecha;
import com.oscar.proyecto.modelos.RecetaUsoProjection;
import com.oscar.proyecto.repositorios.RecetaCocinadaFechaRepository;
import com.oscar.proyecto.repositorios.RecetaRepository;
import com.oscar.proyecto.repositorios.UsuarioRepository;
import com.oscar.proyecto.mapper.RecetaMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecetaCocinadaFechaService {

    private final RecetaCocinadaFechaRepository recetaCocinadaFechaRepository;
    private final RecetaRepository recetaRepository;
    private final UsuarioRepository usuarioRepository;
    private final RecetaMapper recetaMapper;

    public RecetaCocinadaFechaService(
            RecetaCocinadaFechaRepository recetaCocinadaFechaRepository,
            RecetaRepository recetaRepository,
            UsuarioRepository usuarioRepository,
            RecetaMapper recetaMapper) {

        this.recetaCocinadaFechaRepository = recetaCocinadaFechaRepository;
        this.recetaRepository = recetaRepository;
        this.usuarioRepository = usuarioRepository;
        this.recetaMapper = recetaMapper;
    }

    public List<RecetaUsoDTO> getRecetasUltimaSemana() {
        List<RecetaUsoProjection> proyecciones = recetaCocinadaFechaRepository.findRecetasUltimaSemana();
        return recetaMapper.toRecetaUsoDTOList(proyecciones);
    }

    public RecetaUsoDTO guardarRecetaEnFecha(RecetaUsoRequestDTO dto) {

        var receta = recetaRepository.findById(dto.getIdReceta())
                .orElseThrow(() -> new ElementoNoEncontradoException("Receta no encontrada"));

        var usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new ElementoNoEncontradoException("Usuario no encontrado"));

        boolean existe = recetaCocinadaFechaRepository.existsByUsuarioAndReceta(usuario, receta);

        if (existe) {
            throw new ElementoNoEncontradoException("El usuario ya registró esta receta");
        }

        RecetaCocinadaFecha entidad = new RecetaCocinadaFecha();
        entidad.setReceta(receta);
        entidad.setUsuario(usuario);
        entidad.setFechaCocinado(dto.getFecha());

        recetaCocinadaFechaRepository.save(entidad);

        RecetaUsoDTO resultado = new RecetaUsoDTO();
        resultado.setNombreReceta(receta.getTitulo());
        resultado.setVecesCocinada(1L);

        return resultado;
    }

}
