package com.oscar.proyecto.repository;

import com.oscar.proyecto.entity.RecetaIngrediente;
import com.oscar.proyecto.entity.RecetaIngredienteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecetaIngredienteRepository extends JpaRepository<RecetaIngrediente, RecetaIngredienteId> {
    List<RecetaIngrediente> findByRecetaId(Integer idReceta);
    List<RecetaIngrediente> findByIngredienteIdIn(List<Integer> ingredienteIds);
}
