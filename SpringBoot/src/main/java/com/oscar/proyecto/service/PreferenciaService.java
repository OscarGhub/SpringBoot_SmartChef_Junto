package com.oscar.proyecto.service;

import com.oscar.proyecto.dto.Preferencia.PreferenciaDTO;
import com.oscar.proyecto.repository.PreferenciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PreferenciaService {

    private final PreferenciaRepository repo;

    public List<PreferenciaDTO> getAllPreferencias() {
        return repo.findAll().stream()
                .map(p -> new PreferenciaDTO(p.getId(), p.getNombre()))
                .collect(Collectors.toList());
    }
}
