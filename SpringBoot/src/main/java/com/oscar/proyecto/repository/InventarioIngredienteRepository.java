package com.oscar.proyecto.repository;

import com.oscar.proyecto.entity.InventarioIngrediente;
import com.oscar.proyecto.entity.InventarioIngredienteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventarioIngredienteRepository extends JpaRepository<InventarioIngrediente, InventarioIngredienteId> {
    List<InventarioIngrediente> findByInventarioUsuarioId(Integer usuarioId);
}
