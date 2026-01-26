package com.oscar.proyecto.repositorios;

import com.oscar.proyecto.modelos.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecetaCocinadaFechaRepository extends JpaRepository<RecetaCocinadaFecha, RecetaCocinadaFechaId> {
    @Query(value = """
        SELECT
            R.titulo AS nombreReceta, 
            COUNT(H.id_receta) AS vecesCocinada
        FROM
            smartchef.receta R 
        JOIN
            smartchef.historial_cocinado H ON R.id = H.id_receta
        WHERE
            H.fecha_cocinado >= CURRENT_DATE - INTERVAL '7' DAY
        GROUP BY
            R.id, R.titulo
        ORDER BY
            vecesCocinada DESC
        LIMIT 5
        """, nativeQuery = true)
    List<RecetaUsoProjection> findRecetasUltimaSemana();

    boolean existsByUsuarioAndReceta(Usuario usuario, Receta receta);

}
