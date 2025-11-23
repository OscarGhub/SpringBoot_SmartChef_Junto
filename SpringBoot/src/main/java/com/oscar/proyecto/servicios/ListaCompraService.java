package com.oscar.proyecto.servicios;

import com.oscar.proyecto.dto.ListaCompra.IngredienteEnCarritoDTO;
import com.oscar.proyecto.dto.ListaCompra.ListaCompraRequestDTO;
import com.oscar.proyecto.dto.ListaCompra.ListaCompraResponseDTO;
import com.oscar.proyecto.dto.ListaCompra.ListaCompraIngredienteDTO;
import com.oscar.proyecto.modelos.*;
import com.oscar.proyecto.repositorios.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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

        Integer idIngrediente = (request.getIngrediente() != null)
                ? request.getIngrediente().getId()
                : null;

        if (idIngrediente == null) {
            throw new IllegalArgumentException("El ID del ingrediente es requerido.");
        }

        Ingrediente ingrediente = ingredienteRepository.findById(idIngrediente)
                .orElseThrow(() -> new RuntimeException("Ingrediente no encontrado"));

        ListaCompraIngredienteId id = new ListaCompraIngredienteId(idLista, ingrediente.getId());
        ListaCompraIngrediente listaIngrediente = listaCompraIngredienteRepository.findById(id)
                .orElse(new ListaCompraIngrediente());

        listaIngrediente.setId(id);
        listaIngrediente.setListaCompra(listaCompra);
        listaIngrediente.setIngrediente(ingrediente);

        Double cantidad = request.getCantidad() != null ? request.getCantidad() : 1.0;
        listaIngrediente.setCantidad(cantidad);

        listaCompraIngredienteRepository.save(listaIngrediente);

        return crearListaCompraIngredienteDTO(listaIngrediente);
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
                .map(this::crearListaCompraIngredienteDTO)
                .collect(Collectors.toList());
    }

    private ListaCompraIngredienteDTO crearListaCompraIngredienteDTO(ListaCompraIngrediente li) {
        Ingrediente ingredienteEntidad = li.getIngrediente();

        IngredienteEnCarritoDTO ingredienteDto = new IngredienteEnCarritoDTO();
        ingredienteDto.setId(ingredienteEntidad.getId());
        ingredienteDto.setNombre(ingredienteEntidad.getNombre());
        ingredienteDto.setUnidadMedida(ingredienteEntidad.getUnidadMedida());

        ListaCompraIngredienteDTO dto = new ListaCompraIngredienteDTO();
        dto.setIngrediente(ingredienteDto);
        dto.setCantidad(li.getCantidad());

        return dto;
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

        List<RecetaIngrediente> ingredientesReceta = recetaIngredienteRepository.findByRecetaIdEagerly(idReceta);

        for (RecetaIngrediente ri : ingredientesReceta) {

            if (ri.getIngrediente() == null) {
                System.err.println("ERROR: El ingrediente está nulo en la receta " + idReceta + ". Saltando.");
                continue;
            }

            ListaCompraIngredienteId listaIngredienteId = new ListaCompraIngredienteId(
                    listaCompra.getId(),
                    ri.getIngrediente().getId()
            );

            ListaCompraIngrediente listaIngrediente = listaCompraIngredienteRepository.findById(listaIngredienteId)
                    .orElse(new ListaCompraIngrediente());


            Double cantidadActual = (listaIngrediente.getCantidad() != null) ? listaIngrediente.getCantidad() : 0.0;

            Double cantidadAReceta = ri.getCantidad();

            Double nuevaCantidad = cantidadActual + cantidadAReceta;

            listaIngrediente.setId(listaIngredienteId);
            listaIngrediente.setListaCompra(listaCompra);
            listaIngrediente.setIngrediente(ri.getIngrediente());

            listaIngrediente.setCantidad(nuevaCantidad);

            listaCompraIngredienteRepository.save(listaIngrediente);
        }
    }

    public void eliminarRecetaDelCarrito(Integer idUsuario, Integer idReceta) {
        ListaCompra listaCompra = listaCompraRepository.findByUsuarioId(idUsuario)
                .orElseThrow(() -> new RuntimeException("Lista de compra no encontrada para el usuario: " + idUsuario));

        List<RecetaIngrediente> ingredientesReceta = recetaIngredienteRepository.findByRecetaIdEagerly(idReceta);

        for (RecetaIngrediente ri : ingredientesReceta) {

            if (ri.getIngrediente() == null) {
                System.err.println("ERROR: El ingrediente está nulo en la receta " + idReceta + ". Saltando.");
                continue;
            }

            ListaCompraIngredienteId listaIngredienteId = new ListaCompraIngredienteId(
                    listaCompra.getId(),
                    ri.getIngrediente().getId()
            );

            listaCompraIngredienteRepository.findById(listaIngredienteId)
                    .ifPresent(listaIngrediente -> {

                        Double cantidadARestar = ri.getCantidad();

                        Double cantidadActual = listaIngrediente.getCantidad();

                        double nuevaCantidad = cantidadActual - cantidadARestar;

                        if (nuevaCantidad > 0) {
                            listaIngrediente.setCantidad(nuevaCantidad);
                            listaCompraIngredienteRepository.save(listaIngrediente);

                        } else {
                            listaCompraIngredienteRepository.delete(listaIngrediente);
                        }
                    });
        }
    }

    public boolean recetaEstaEnCarrito(Integer idUsuario, Integer idReceta) {
        ListaCompra listaCompra = listaCompraRepository.findByUsuarioId(idUsuario).orElse(null);
        if (listaCompra == null) {
            return false;
        }

        List<RecetaIngrediente> ingredientesReceta = recetaIngredienteRepository.findByRecetaIdEagerly(idReceta);

        if (ingredientesReceta.isEmpty()) {
            return false;
        }

        Map<Integer, Double> cantidadesRequeridas = ingredientesReceta.stream()
                .collect(Collectors.toMap(
                        ri -> ri.getIngrediente().getId(),
                        RecetaIngrediente::getCantidad
                ));

        List<Integer> idsIngredientesRequeridos = new java.util.ArrayList<>(cantidadesRequeridas.keySet());

        List<ListaCompraIngrediente> ingredientesEnLista = listaCompraIngredienteRepository.findByListaAndIngredientesIds(
                listaCompra.getId(),
                idsIngredientesRequeridos
        );

        if (ingredientesEnLista.size() != cantidadesRequeridas.size()) {
            return false;
        }

        for (RecetaIngrediente ri : ingredientesReceta) {
            Integer ingredienteId = ri.getIngrediente().getId();
            Double cantidadRequerida = ri.getCantidad();

            Optional<ListaCompraIngrediente> match = ingredientesEnLista.stream()
                    .filter(lci -> lci.getIngrediente().getId().equals(ingredienteId))
                    .findFirst();

            if (match.isEmpty()) {
                return false;
            }

            Double cantidadPresente = match.get().getCantidad();

            if (cantidadPresente < cantidadRequerida) {
                return false;
            }
        }

        return true;
    }

}