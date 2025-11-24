package com.oscar.proyecto.servicios;

import com.oscar.proyecto.dto.Inventario.InventarioResponseDTO;
import com.oscar.proyecto.dto.InventarioIngrediente.InventarioIngredienteResponseDTO; // ⬅️ NUEVO
import com.oscar.proyecto.modelos.*;
import com.oscar.proyecto.repositorios.InventarioRepository;
import com.oscar.proyecto.repositorios.UsuarioRepository;
import com.oscar.proyecto.repositorios.InventarioIngredienteRepository;
import com.oscar.proyecto.repositorios.IngredienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventarioService {

    private final InventarioRepository inventarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final IngredienteRepository ingredienteRepository;
    private final InventarioIngredienteRepository inventarioIngredienteRepository;
    private final InventarioIngredienteService inventarioIngredienteService;

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

    public List<InventarioIngredienteResponseDTO> obtenerIngredientesDeInventarioPorUsuario(Integer usuarioId) {
        return inventarioIngredienteService.obtenerInventarioPorUsuario(usuarioId);
    }

    public InventarioIngrediente agregarIngredienteAlInventario(Integer idInventario, Integer idIngrediente, BigDecimal cantidad) {
        Inventario inventario = inventarioRepository.findById(idInventario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventario no encontrado"));

        Ingrediente ingrediente = ingredienteRepository.findById(idIngrediente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingrediente no encontrado"));

        InventarioIngrediente inventarioIngrediente = inventarioIngredienteRepository
                .findByInventarioIdAndIngredienteId(idInventario, idIngrediente)
                .orElse(null);

        if (inventarioIngrediente != null) {
            inventarioIngrediente.setCantidad(inventarioIngrediente.getCantidad().add(cantidad));
        } else {
            inventarioIngrediente = new InventarioIngrediente();
            inventarioIngrediente.setInventario(inventario);
            inventarioIngrediente.setIngrediente(ingrediente);
            inventarioIngrediente.setCantidad(cantidad);
        }

        return inventarioIngredienteRepository.save(inventarioIngrediente);
    }

    public void eliminarIngredienteDelInventario(Integer idInventario, Integer idIngrediente) {
        inventarioIngredienteService.eliminarIngredienteInventario(idInventario, idIngrediente);
    }
}