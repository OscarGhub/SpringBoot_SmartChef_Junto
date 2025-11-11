package com.oscar.proyecto.repository;

import com.oscar.proyecto.entity.ListaCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ListaCompraRepository extends JpaRepository<ListaCompra, Integer> {
    Optional<ListaCompra> findByUsuarioId(Integer usuarioId);
}
