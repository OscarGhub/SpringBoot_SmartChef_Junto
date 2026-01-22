package com.oscar.proyecto.repositorios;

import com.oscar.proyecto.modelos.RecetaGuardada;
import com.oscar.proyecto.modelos.RecetaGuardadaId;
import com.oscar.proyecto.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecetaGuardadaRepository extends JpaRepository<RecetaGuardada, RecetaGuardadaId> {

    @Query("SELECT rg FROM RecetaGuardada rg WHERE rg.id.idUsuario = :idUsuario")
    List<RecetaGuardada> findAllByIdUsuario(@Param("idUsuario") Integer idUsuario);

    @Query("SELECT rg.usuario FROM RecetaGuardada rg WHERE rg.id.idReceta = :idReceta")
    List<Usuario> findUsuariosPorReceta(@Param("idReceta") Integer idReceta);

    @Query(value = "SELECT id_receta FROM smartchef.receta_guardada GROUP BY id_receta ORDER BY COUNT(*) DESC LIMIT 1", nativeQuery = true)
    Integer findRecetaMasGuardada();

    @Query("SELECT COUNT(rg) FROM RecetaGuardada rg WHERE rg.receta.id = :idReceta")
    int contarGuardados(@Param("idReceta") Integer idReceta);

    long countByRecetaId(Integer recetaId);

}
