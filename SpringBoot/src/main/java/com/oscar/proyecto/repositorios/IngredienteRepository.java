package com.oscar.proyecto.repositorios;

import com.oscar.proyecto.modelos.Ingrediente;
import com.oscar.proyecto.modelos.IngredienteUsoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredienteRepository extends JpaRepository<Ingrediente, Integer> {
    @Query(value = """
        SELECT
            I.nombre AS Ingrediente,
            COUNT(RI.id_ingrediente) AS vecesUtilizado 
        FROM
            smartchef.Ingrediente I
        JOIN
            smartchef.Receta_Ingrediente RI ON I.id = RI.id_ingrediente
        GROUP BY
            I.id, I.nombre
        ORDER BY
            vecesUtilizado DESC
        LIMIT 5
        """, nativeQuery = true)
    List<IngredienteUsoProjection> findTop5UsedIngredientsProjection();
}
