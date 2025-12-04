package com.oscar.proyecto.servicios;

import com.oscar.proyecto.dto.Inventario.InventarioResponseDTO;
import com.oscar.proyecto.dto.InventarioIngrediente.InventarioIngredienteRequestDTO;
import com.oscar.proyecto.dto.InventarioIngrediente.InventarioIngredienteResponseDTO;
import com.oscar.proyecto.exception.ElementoNoEncontradoException;
import com.oscar.proyecto.modelos.Inventario;
import com.oscar.proyecto.modelos.Usuario;
import com.oscar.proyecto.repositorios.InventarioRepository;
import com.oscar.proyecto.repositorios.UsuarioRepository;
import com.oscar.proyecto.mapper.InventarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventarioService {

    private final InventarioRepository inventarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final InventarioIngredienteService inventarioIngredienteService;
    private final InventarioMapper inventarioMapper;

    public Inventario crearInventarioParaUsuario(Usuario usuario) {
        if (usuario.getInventario() != null) return usuario.getInventario();

        Inventario inventario = new Inventario();
        inventario.setUsuario(usuario);

        return inventarioRepository.save(inventario);
    }

    public List<InventarioResponseDTO> obtenerInventariosPorUsuario(Integer usuarioId) {
        List<Inventario> inventarios = inventarioRepository.findByUsuarioId(usuarioId);
        return inventarioMapper.toResponseDTOList(inventarios);
    }

    public Inventario crearInventarioParaUsuarioPorId(Integer usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ElementoNoEncontradoException("Usuario no encontrado"));

        return crearInventarioParaUsuario(usuario);
    }

    public List<InventarioIngredienteResponseDTO> obtenerIngredientesDeInventarioPorUsuario(Integer usuarioId) {
        return inventarioIngredienteService.obtenerInventarioPorUsuario(usuarioId);
    }

    public InventarioIngredienteResponseDTO agregarIngredienteAlInventario(
            Integer idInventario,
            Integer idIngrediente,
            BigDecimal cantidad) {

        InventarioIngredienteRequestDTO request = new InventarioIngredienteRequestDTO(
                idInventario,
                idIngrediente,
                cantidad
        );

        return inventarioIngredienteService.agregarIngredienteInventario(request);
    }

    public void eliminarIngredienteDelInventario(Integer idInventario, Integer idIngrediente) {
        inventarioIngredienteService.eliminarIngredienteInventario(idInventario, idIngrediente);
    }
}