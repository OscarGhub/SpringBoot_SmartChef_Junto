import { Component, OnInit, inject } from '@angular/core';
import { IonicModule } from '@ionic/angular';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AlertService } from '../servicios/alert.service';
import { UsuarioService } from '../servicios/usuario/usuario.service';

@Component({
  selector: 'app-tarjeta-perfil',
  templateUrl: './tarjeta-perfil.component.html',
  styleUrls: ['./tarjeta-perfil.component.scss'],
  standalone: true,
  imports: [
    IonicModule,
    FormsModule,
    CommonModule
  ]
})
export class TarjetaPerfilComponent implements OnInit {
  private router = inject(Router);
  private alertService = inject(AlertService);
  private usuarioService = inject(UsuarioService);

  usuario: any = null;

  editarFecha = false;
  editarEmail = false;

  ngOnInit() {
    const data = localStorage.getItem('usuario');
    if (data) {
      this.usuario = JSON.parse(data);
    }
  }

  toggleEditar(campo: 'fecha_nacimiento' | 'correo_electronico') {
    if (campo === 'fecha_nacimiento') this.editarFecha = !this.editarFecha;
    if (campo === 'correo_electronico') this.editarEmail = !this.editarEmail;
  }

  irAInventario() {
    this.router.navigate(['/inventario']);
  }

  async guardarCampo(campo: 'fecha_nacimiento' | 'correo_electronico') {
    try {

      await this.usuarioService.actualizarUsuario(this.usuario).toPromise();

      localStorage.setItem('usuario', JSON.stringify(this.usuario));

      if (campo === 'fecha_nacimiento') this.editarFecha = false;
      if (campo === 'correo_electronico') this.editarEmail = false;

      await this.alertService.mostrarAlerta('Éxito', 'Datos actualizados correctamente');
    } catch (error) {
      await this.alertService.mostrarAlerta('Error', 'No se pudo actualizar. Intenta más tarde.');
    }
  }

}
