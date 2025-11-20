package com.oscar.proyecto.repositorios;

import com.oscar.proyecto.modelos.ListaCompra;
import com.oscar.proyecto.modelos.ListaCompraIngrediente;
import com.oscar.proyecto.modelos.ListaCompraIngredienteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ListaCompraIngredienteRepository extends JpaRepository<ListaCompraIngrediente, ListaCompraIngredienteId> {
    List<ListaCompraIngrediente> findByListaCompra(ListaCompra listaCompra);
}