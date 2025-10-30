package com.oscar.proyecto.repository;

import com.oscar.proyecto.entity.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecetaRepository extends JpaRepository<Receta, Integer> {

    @Query("SELECT r FROM Receta r JOIN r.preferencias p WHERE p.id IN :preferenciaIds")
    List<Receta> findByPreferenciasIn(List<Integer> preferenciaIds);

    @Query("""
        SELECT r, COUNT(rg) AS numFavoritos
        FROM Receta r
        LEFT JOIN RecetaGuardada rg ON rg.receta = r
        GROUP BY r
    """)
    List<Object[]> findAllWithNumFavoritos();
}
