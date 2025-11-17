package com.oscar.proyecto.dto.Usuario;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UsuarioDTO {

    private String nombre;
    private String correoElectronico;
    private String contrasena;
    private String confirmarContrasena;
    private LocalDate fechaNacimiento;
}
