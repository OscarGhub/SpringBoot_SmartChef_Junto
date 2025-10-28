import { Component, OnInit, inject, ViewChild, ElementRef } from '@angular/core';
import { IonicModule } from '@ionic/angular';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AlertService } from '../../servicios/alert.service';
import { UsuarioService } from '../../servicios/usuario/usuario.service';

@Component({
  selector: 'app-tarjeta-perfil',
  templateUrl: './tarjeta-perfil.component.html',
  styleUrls: ['./tarjeta-perfil.component.scss'],
  standalone: true,
  imports: [IonicModule, FormsModule, CommonModule]
})
export class TarjetaPerfilComponent implements OnInit {
  private router = inject(Router);
  private alertService = inject(AlertService);
  private usuarioService = inject(UsuarioService);

  usuario: any = null;
  editarFecha = false;
  editarEmail = false;

  @ViewChild('fileInput') fileInput!: ElementRef<HTMLInputElement>;

  ngOnInit() {
    const data = localStorage.getItem('usuario');
    if (data) this.usuario = JSON.parse(data);
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
      const usuarioActualizado = await this.usuarioService.actualizarUsuario(this.usuario).toPromise();
      this.usuario = usuarioActualizado;
      localStorage.setItem('usuario', JSON.stringify(usuarioActualizado));
      if (campo === 'fecha_nacimiento') this.editarFecha = false;
      if (campo === 'correo_electronico') this.editarEmail = false;
      await this.alertService.mostrarAlerta('Éxito', 'Datos actualizados correctamente');
    } catch {
      await this.alertService.mostrarAlerta('Error', 'No se pudo actualizar. Intenta más tarde.');
    }
  }

  seleccionarArchivo() {
    this.fileInput.nativeElement.click();
  }

  async cambiarFoto(event: Event) {
    const input = event.target as HTMLInputElement;
    if (!input.files || input.files.length === 0) return;

    const archivo = input.files[0];
    const formData = new FormData();
    formData.append('foto', archivo);

    try {
      const usuarioActualizado = await this.usuarioService.actualizarFoto(this.usuario.id, formData).toPromise();
      const timestamp = new Date().getTime();
      this.usuario = {
        ...usuarioActualizado,
        foto_url: `http://localhost:8080/api/usuario/${this.usuario.id}/foto?t=${timestamp}`
      };
      localStorage.setItem('usuario', JSON.stringify(this.usuario));
      await this.alertService.mostrarAlerta('Éxito', 'Foto actualizada correctamente');
    } catch {
      await this.alertService.mostrarAlerta('Error', 'No se pudo actualizar la foto.');
    } finally {
      input.value = '';
    }
  }
}
