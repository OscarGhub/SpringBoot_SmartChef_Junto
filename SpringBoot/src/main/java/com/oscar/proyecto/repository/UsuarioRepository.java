package com.oscar.proyecto.repository;

import com.oscar.proyecto.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {}
