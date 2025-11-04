package com.oscar.proyecto.repository;

import com.oscar.proyecto.entity.ListaCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListaCompraRepository extends JpaRepository<ListaCompra, Integer> {
}
