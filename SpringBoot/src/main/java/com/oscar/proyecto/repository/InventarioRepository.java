package com.oscar.proyecto.repository;

import com.oscar.proyecto.entity.Inventario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventarioRepository extends CrudRepository<Inventario, Integer> {

    @Query("SELECT ii.id.idIngrediente, i.id, ii.ingrediente.nombre, ii.ingrediente.unidadMedida, ii.ingrediente.imagen_url, ii.cantidad " +
            "FROM InventarioIngrediente ii " +
            "JOIN ii.inventario i " +
            "WHERE i.usuario.id = :usuarioId")

    List<Object[]> findInventarioConIngredientePorUsuario(Integer usuarioId);

    List<Inventario> findByUsuarioId(Integer usuarioId);
}
