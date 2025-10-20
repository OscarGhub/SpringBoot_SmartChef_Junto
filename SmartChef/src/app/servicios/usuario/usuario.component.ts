import { Component, OnInit } from '@angular/core';
import { Usuario } from './usuario.model';
import { UsuarioService } from './usuario.service';

@Component({
  selector: 'app-usuario',
  templateUrl: './registro.component.html',
})
export class UsuarioComponent implements OnInit {
  usuarios: Usuario[] = [];
  nuevoUsuario: Usuario = {
    correo_electronico: '',
    contrasena: '',
  };

  constructor(private usuarioService: UsuarioService) {}

  ngOnInit(): void {
    this.cargarUsuarios();
  }

  cargarUsuarios(): void {
    this.usuarioService.getUsuarios().subscribe({
      next: (data) => {
        this.usuarios = data;
      },
      error: (err) => {
        console.error('Error al cargar usuarios', err);
      },
    });
  }

  agregarUsuario(): void {
    if (
      !this.nuevoUsuario.correo_electronico ||
      !this.nuevoUsuario.contrasena
    ) {
      alert('Correo y contraseña son obligatorios');
      return;
    }

    this.usuarioService.crearUsuario(this.nuevoUsuario).subscribe({
      next: (usuarioCreado) => {
        this.usuarios.push(usuarioCreado);
        this.nuevoUsuario = { correo_electronico: '', contrasena: '' };
      },
      error: (err) => {
        console.error('Error al crear usuario', err);
      },
    });
  }
}
