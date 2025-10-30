package com.oscar.proyecto.repository;

import com.oscar.proyecto.entity.RecetaGuardada;
import com.oscar.proyecto.entity.RecetaGuardadaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecetaGuardadaRepository extends JpaRepository<RecetaGuardada, RecetaGuardadaId> {
}
