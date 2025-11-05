package com.oscar.proyecto.repository;

import com.oscar.proyecto.entity.ListaCompra;
import com.oscar.proyecto.entity.ListaCompraIngrediente;
import com.oscar.proyecto.entity.ListaCompraIngredienteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ListaCompraIngredienteRepository extends JpaRepository<ListaCompraIngrediente, ListaCompraIngredienteId> {
    List<ListaCompraIngrediente> findByListaCompra(ListaCompra listaCompra);
}