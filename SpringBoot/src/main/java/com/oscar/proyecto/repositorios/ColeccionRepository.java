package com.oscar.proyecto.repositorios;

import com.oscar.proyecto.modelos.Coleccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColeccionRepository extends JpaRepository<Coleccion, Integer> {

    List<Coleccion> findByUsuarioId(Integer idUsuario);
}