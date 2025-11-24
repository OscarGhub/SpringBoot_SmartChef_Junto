package com.oscar.proyecto.repositorios;

import com.oscar.proyecto.modelos.ListaCompra;
import com.oscar.proyecto.modelos.ListaCompraIngrediente;
import com.oscar.proyecto.modelos.ListaCompraIngredienteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ListaCompraIngredienteRepository extends JpaRepository<ListaCompraIngrediente, ListaCompraIngredienteId> {
    List<ListaCompraIngrediente> findByListaCompra(ListaCompra listaCompra);

    @Query("SELECT lci FROM ListaCompraIngrediente lci WHERE lci.listaCompra.id = :idLista AND lci.ingrediente.id IN :idsIngredientes")
    List<ListaCompraIngrediente> findByListaAndIngredientesIds(@Param("idLista") Integer idLista,
                                                               @Param("idsIngredientes") List<Integer> idsIngredientes);
}