package com.oscar.proyecto.repositorios;

import com.oscar.proyecto.modelos.InventarioIngrediente;
import com.oscar.proyecto.modelos.InventarioIngredienteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventarioIngredienteRepository extends JpaRepository<InventarioIngrediente, InventarioIngredienteId> {
    List<InventarioIngrediente> findByInventarioUsuarioId(Integer usuarioId);
    Optional<InventarioIngrediente> findByInventarioIdAndIngredienteId(Integer idInventario, Integer idIngrediente);
}
