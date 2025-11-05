import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Usuario } from '../../servicios/usuario/usuario.model';
import { UsuarioService } from '../../servicios/usuario/usuario.service';
import { IonicModule } from '@ionic/angular';
import { AlertService } from '../../servicios/alert.service';
import { UsuarioHelper } from '../../servicios/usuario.helper';

@Component({
  selector: 'app-registro',
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    IonicModule
  ]
})
export class RegistroComponent {
  private usuarioService = inject(UsuarioService);
  private alertService = inject(AlertService);

  nuevoUsuario: Usuario = {
    nombre: '',
    correoElectronico: '',
    contrasena: '',
    fechaNacimiento: '',
  };

  confirmar_contrasena: string = '';

  async agregarUsuario(): Promise<void> {

    const errorValidacion = UsuarioHelper.validarCampos(this.nuevoUsuario, this.confirmar_contrasena);
    if (errorValidacion) {
      await this.alertService.mostrarAlerta('Error', errorValidacion);
      return;
    }

    const usuarioConHash: Usuario = {
      ...this.nuevoUsuario,
      contrasena: UsuarioHelper.hashearContrasena(this.nuevoUsuario.contrasena)
    };

    this.usuarioService.crearUsuario(usuarioConHash).subscribe({
      next: async (usuarioCreado) => {
        await this.alertService.mostrarAlerta(
          'Usuario registrado',
          `El usuario ${usuarioCreado.nombre ?? usuarioCreado.correoElectronico} ha sido creado exitosamente.`
        );
        this.resetFormulario();
      },
      error: async () => {
        await this.alertService.mostrarAlerta('Error', 'No se pudo crear el usuario. Intenta más tarde.');
      }
    });
  }

  private resetFormulario(): void {
    this.nuevoUsuario = {
      nombre: '',
      correoElectronico: '',
      contrasena: '',
      fechaNacimiento: ''
    };
    this.confirmar_contrasena = '';
  }

  getFechaMaxima(): string {
    const hoy = new Date();
    hoy.setFullYear(hoy.getFullYear() - 18);
    return hoy.toISOString().split('T')[0];
  }

  getFechaFormateada(fechaISO: string): string {
    if (!fechaISO) return '';
    const fecha = new Date(fechaISO);
    return fecha.toLocaleDateString('es-ES', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric'
    });
  }
}
