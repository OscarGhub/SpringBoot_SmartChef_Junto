package com.oscar.proyecto.repositorios;

import com.oscar.proyecto.modelos.Inventario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventarioRepository extends CrudRepository<Inventario, Integer> {

    List<Inventario> findByUsuarioId(Integer usuarioId);
}
