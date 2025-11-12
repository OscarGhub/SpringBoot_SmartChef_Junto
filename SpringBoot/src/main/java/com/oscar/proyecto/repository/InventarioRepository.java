package com.oscar.proyecto.repository;

import com.oscar.proyecto.entity.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Integer> {
    List<Inventario> findByUsuarioId(Integer usuarioId);
}
