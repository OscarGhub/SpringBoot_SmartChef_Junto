package com.oscar.proyecto.repository;

import com.oscar.proyecto.entity.RecetaGuardada;
import com.oscar.proyecto.entity.RecetaGuardadaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecetaGuardadaRepository extends JpaRepository<RecetaGuardada, RecetaGuardadaId> {

    @Query("SELECT rg FROM RecetaGuardada rg WHERE rg.id.id_usuario = :idUsuario")
    List<RecetaGuardada> findAllByIdUsuario(@Param("idUsuario") Integer idUsuario);

}