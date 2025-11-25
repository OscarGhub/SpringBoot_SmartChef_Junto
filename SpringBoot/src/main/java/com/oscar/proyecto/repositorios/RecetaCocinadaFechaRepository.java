package com.oscar.proyecto.repositorios;

import com.oscar.proyecto.modelos.RecetaCocinadaFecha;
import com.oscar.proyecto.modelos.RecetaCocinadaFechaId;
import com.oscar.proyecto.modelos.RecetaUsoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecetaCocinadaFechaRepository extends JpaRepository<RecetaCocinadaFecha, RecetaCocinadaFechaId> {
    @Query(value = """
        SELECT
            R.titulo AS NombreReceta, 
            COUNT(H.id_receta) AS vecesCocinada
        FROM
            receta R
        JOIN
            historial_cocinado H ON R.id = H.id_receta
        WHERE
            H.fecha_cocinado >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) 
        GROUP BY
            R.id, R.titulo
        ORDER BY
            vecesCocinada DESC
        LIMIT 5
        """, nativeQuery = true)
    List<RecetaUsoProjection> findRecetasUltimaSemana();
}
