package com.oscar.proyecto.service;

import com.oscar.proyecto.dto.Inventario.InventarioRequestDTO;
import com.oscar.proyecto.dto.Inventario.InventarioResponseDTO;
import com.oscar.proyecto.entity.Inventario;
import com.oscar.proyecto.entity.Usuario;
import com.oscar.proyecto.repository.InventarioRepository;
import com.oscar.proyecto.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventarioService {

    private final InventarioRepository inventarioRepository;
    private final UsuarioRepository usuarioRepository;

    public Inventario crearInventarioParaUsuario(Usuario usuario) {
        if (usuario.getInventario() != null) return usuario.getInventario();

        Inventario inventario = new Inventario();
        inventario.setUsuario(usuario);

        return inventarioRepository.save(inventario);
    }

    public List<InventarioResponseDTO> obtenerInventariosPorUsuario(Integer usuarioId) {
        List<Inventario> inventarios = inventarioRepository.findByUsuarioId(usuarioId);
        return inventarios.stream()
                .map(i -> new InventarioResponseDTO(i.getId(), i.getUsuario().getId()))
                .collect(Collectors.toList());
    }

    public Inventario crearInventarioParaUsuarioPorId(Integer usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        return crearInventarioParaUsuario(usuario);
    }

}
