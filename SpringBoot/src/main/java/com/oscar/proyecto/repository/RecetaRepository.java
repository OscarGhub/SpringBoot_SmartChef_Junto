package com.oscar.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.oscar.proyecto.entity.Receta;

public interface RecetaRepository extends JpaRepository<Receta, Integer> {}
