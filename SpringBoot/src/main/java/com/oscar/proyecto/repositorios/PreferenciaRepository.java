package com.oscar.proyecto.repositorios;

import com.oscar.proyecto.modelos.Preferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreferenciaRepository extends JpaRepository<Preferencia, Integer> { }
