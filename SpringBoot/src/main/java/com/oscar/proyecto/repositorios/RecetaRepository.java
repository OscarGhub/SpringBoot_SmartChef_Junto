package com.oscar.proyecto.repositorios;

import com.oscar.proyecto.modelos.Receta;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecetaRepository extends JpaRepository<Receta, Integer> {

    @Query("SELECT DISTINCT r FROM Receta r JOIN r.preferencias p WHERE p.id IN :preferenciaIds")
    List<Receta> findByPreferenciasIn(@Param("preferenciaIds") List<Integer> preferenciaIds);

}
