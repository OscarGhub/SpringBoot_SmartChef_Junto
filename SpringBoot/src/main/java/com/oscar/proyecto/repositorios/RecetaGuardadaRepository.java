package com.oscar.proyecto.repositorios;

import com.oscar.proyecto.modelos.RecetaGuardada;
import com.oscar.proyecto.modelos.RecetaGuardadaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecetaGuardadaRepository extends JpaRepository<RecetaGuardada, RecetaGuardadaId> {

    @Query("SELECT rg FROM RecetaGuardada rg WHERE rg.id.id_usuario = :idUsuario")
    List<RecetaGuardada> findAllByIdUsuario(@Param("idUsuario") Integer idUsuario);

}