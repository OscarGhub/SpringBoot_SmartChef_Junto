package com.oscar.proyecto.repository;

import com.oscar.proyecto.entity.Receta;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecetaRepository extends JpaRepository<Receta, Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE Receta r SET r.numFavoritos = r.numFavoritos + 1 WHERE r.id = :id")
    void incrementarNumFavoritos(@Param("id") Integer id);

    @Transactional
    @Modifying
    @Query("UPDATE Receta r SET r.numFavoritos = r.numFavoritos - 1 WHERE r.id = :id AND r.numFavoritos > 0")
    void decrementarNumFavoritos(@Param("id") Integer id);

    @Query("SELECT DISTINCT r FROM Receta r JOIN r.preferencias p WHERE p.id IN :preferenciaIds")
    List<Receta> findByPreferenciasIn(@Param("preferenciaIds") List<Integer> preferenciaIds);

}
