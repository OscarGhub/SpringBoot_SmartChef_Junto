package com.oscar.proyecto.repository;

import com.oscar.proyecto.entity.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecetaRepository extends JpaRepository<Receta, Integer> {

    @Query("SELECT r FROM Receta r JOIN r.preferencias p WHERE p.id IN :preferenciaIds")
    List<Receta> findByPreferenciasIn(@Param("preferenciaIds") List<Integer> preferenciaIds);
}