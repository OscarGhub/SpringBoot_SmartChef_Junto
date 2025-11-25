package com.oscar.proyecto.servicios;

import com.oscar.proyecto.dto.Preferencia.PreferenciaDTO;
import com.oscar.proyecto.repositorios.PreferenciaRepository;
import com.oscar.proyecto.mapper.PreferenciaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PreferenciaService {

    private final PreferenciaRepository repo;
    private final PreferenciaMapper preferenciaMapper;

    public List<PreferenciaDTO> getAllPreferencias() {
        return preferenciaMapper.toDTOList(repo.findAll());
    }
}