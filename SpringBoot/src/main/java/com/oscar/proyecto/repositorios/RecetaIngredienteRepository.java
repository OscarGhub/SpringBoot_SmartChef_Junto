package com.oscar.proyecto.repositorios;

import com.oscar.proyecto.modelos.RecetaIngrediente;
import com.oscar.proyecto.modelos.RecetaIngredienteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecetaIngredienteRepository extends JpaRepository<RecetaIngrediente, RecetaIngredienteId> {
    List<RecetaIngrediente> findByRecetaId(Integer idReceta);
}
