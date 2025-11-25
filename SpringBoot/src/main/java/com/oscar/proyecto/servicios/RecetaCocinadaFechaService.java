package com.oscar.proyecto.servicios;

import com.oscar.proyecto.dto.Receta.RecetaUsoDTO;
import com.oscar.proyecto.modelos.RecetaUsoProjection;
import com.oscar.proyecto.repositorios.RecetaCocinadaFechaRepository;
import com.oscar.proyecto.mapper.RecetaMapper;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RecetaCocinadaFechaService {

    private final RecetaCocinadaFechaRepository recetaCocinadaFechaRepository;
    private final RecetaMapper recetaMapper;

    public RecetaCocinadaFechaService(
            RecetaCocinadaFechaRepository recetaCocinadaFechaRepository,
            RecetaMapper recetaMapper) {

        this.recetaCocinadaFechaRepository = recetaCocinadaFechaRepository;
        this.recetaMapper = recetaMapper;
    }

    public List<RecetaUsoDTO> getRecetasUltimaSemana() {

        List<RecetaUsoProjection> proyecciones = recetaCocinadaFechaRepository.findRecetasUltimaSemana();

        return recetaMapper.toRecetaUsoDTOList(proyecciones);
    }
}