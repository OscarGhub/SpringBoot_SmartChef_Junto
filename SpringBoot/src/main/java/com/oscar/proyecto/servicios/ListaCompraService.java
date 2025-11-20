package com.oscar.proyecto.servicios;

import com.oscar.proyecto.dto.ListaCompra.ListaCompraRequestDTO;
import com.oscar.proyecto.dto.ListaCompra.ListaCompraResponseDTO;
import com.oscar.proyecto.dto.ListaCompra.ListaCompraIngredienteDTO;
import com.oscar.proyecto.modelos.*;
import com.oscar.proyecto.repositorios.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListaCompraService {

    private final ListaCompraRepository listaCompraRepository;
    private final ListaCompraIngredienteRepository listaCompraIngredienteRepository;
    private final IngredienteRepository ingredienteRepository;
    private final RecetaIngredienteRepository recetaIngredienteRepository;

    public ListaCompraResponseDTO crearListaCompra(ListaCompraRequestDTO request) {
        ListaCompra listaCompra = new ListaCompra();
        Usuario usuario = new Usuario();
        usuario.setId(request.getIdUsuario());
        listaCompra.setUsuario(usuario);
        listaCompra.setFecha_creacion(LocalDate.now());

        ListaCompra guardada = listaCompraRepository.save(listaCompra);

        return new ListaCompraResponseDTO(
                guardada.getId(),
                guardada.getUsuario().getId(),
                guardada.getFecha_creacion()
        );
    }

    public List<ListaCompraResponseDTO> obtenerListasPorUsuario(Integer idUsuario) {
        Optional<ListaCompra> listas = listaCompraRepository.findByUsuarioId(idUsuario);
        return listas.stream()
                .map(lc -> new ListaCompraResponseDTO(
                        lc.getId(),
                        lc.getUsuario().getId(),
                        lc.getFecha_creacion()
                ))
                .collect(Collectors.toList());
    }

    public ListaCompraIngredienteDTO anadirIngrediente(Integer idLista, ListaCompraIngredienteDTO request) {
        ListaCompra listaCompra = listaCompraRepository.findById(idLista)
                .orElseThrow(() -> new RuntimeException("Lista de compra no encontrada"));

        Ingrediente ingrediente = ingredienteRepository.findById(request.getIdIngrediente())
                .orElseThrow(() -> new RuntimeException("Ingrediente no encontrado"));

        ListaCompraIngredienteId id = new ListaCompraIngredienteId(idLista, ingrediente.getId());
        ListaCompraIngrediente listaIngrediente = listaCompraIngredienteRepository.findById(id)
                .orElse(new ListaCompraIngrediente());

        listaIngrediente.setId(id);
        listaIngrediente.setListaCompra(listaCompra);
        listaIngrediente.setIngrediente(ingrediente);
        listaIngrediente.setCantidad(request.getCantidad() != null ? request.getCantidad() : 1.0);

        listaCompraIngredienteRepository.save(listaIngrediente);

        request.setNombreIngrediente(ingrediente.getNombre());
        return request;
    }

    public void eliminarIngrediente(Integer idLista, Integer idIngrediente) {
        ListaCompraIngredienteId id = new ListaCompraIngredienteId(idLista, idIngrediente);

        if (!listaCompraIngredienteRepository.existsById(id)) {
            throw new RuntimeException("Ingrediente no existe en la lista");
        }

        listaCompraIngredienteRepository.deleteById(id);
    }

    public List<ListaCompraIngredienteDTO> obtenerIngredientes(Integer idLista) {
        ListaCompra listaCompra = listaCompraRepository.findById(idLista)
                .orElseThrow(() -> new RuntimeException("Lista de compra no encontrada"));

        List<ListaCompraIngrediente> ingredientes = listaCompraIngredienteRepository.findByListaCompra(listaCompra);

        return ingredientes.stream()
                .map(li -> {
                    ListaCompraIngredienteDTO dto = new ListaCompraIngredienteDTO();
                    dto.setIdIngrediente(li.getIngrediente().getId());
                    dto.setNombreIngrediente(li.getIngrediente().getNombre());
                    dto.setCantidad(li.getCantidad());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public void anadirRecetaAlCarrito(Integer idUsuario, Integer idReceta) {
        ListaCompra listaCompra = listaCompraRepository.findByUsuarioId(idUsuario)
                .orElseGet(() -> {
                    ListaCompra nuevaLista = new ListaCompra();
                    Usuario usuario = new Usuario();
                    usuario.setId(idUsuario);
                    nuevaLista.setUsuario(usuario);
                    nuevaLista.setFecha_creacion(LocalDate.now());
                    return listaCompraRepository.save(nuevaLista);
                });

        List<RecetaIngrediente> ingredientesReceta = recetaIngredienteRepository.findByRecetaId(idReceta);

        for (RecetaIngrediente ri : ingredientesReceta) {
            ListaCompraIngredienteId listaIngredienteId = new ListaCompraIngredienteId(
                    listaCompra.getId(),
                    ri.getIngrediente().getId()
            );

            ListaCompraIngrediente listaIngrediente = listaCompraIngredienteRepository.findById(listaIngredienteId)
                    .orElse(new ListaCompraIngrediente());

            listaIngrediente.setId(listaIngredienteId);
            listaIngrediente.setListaCompra(listaCompra);
            listaIngrediente.setIngrediente(ri.getIngrediente());
            listaIngrediente.setCantidad(ri.getCantidad());

            listaCompraIngredienteRepository.save(listaIngrediente);
        }
    }

    public void eliminarRecetaDelCarrito(Integer idUsuario, Integer idReceta) {
        ListaCompra listaCompra = listaCompraRepository.findByUsuarioId(idUsuario)
                .orElseThrow(() -> new RuntimeException("Lista de compra no encontrada"));

        List<RecetaIngrediente> ingredientesReceta = recetaIngredienteRepository.findByRecetaId(idReceta);

        for (RecetaIngrediente ri : ingredientesReceta) {
            ListaCompraIngredienteId listaIngredienteId = new ListaCompraIngredienteId(
                    listaCompra.getId(),
                    ri.getIngrediente().getId()
            );

            if (listaCompraIngredienteRepository.existsById(listaIngredienteId)) {
                listaCompraIngredienteRepository.deleteById(listaIngredienteId);
            }
        }
    }
}
