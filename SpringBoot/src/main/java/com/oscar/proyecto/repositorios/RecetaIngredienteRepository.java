package com.oscar.proyecto.repositorios;

import com.oscar.proyecto.modelos.RecetaIngrediente;
import com.oscar.proyecto.modelos.RecetaIngredienteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecetaIngredienteRepository extends JpaRepository<RecetaIngrediente, RecetaIngredienteId> {
    List<RecetaIngrediente> findByRecetaId(Integer idReceta);

    @Query("SELECT ri FROM RecetaIngrediente ri JOIN FETCH ri.ingrediente WHERE ri.id.idReceta = :idReceta")
    List<RecetaIngrediente> findByRecetaIdEagerly(@Param("idReceta") Integer idReceta);
}
