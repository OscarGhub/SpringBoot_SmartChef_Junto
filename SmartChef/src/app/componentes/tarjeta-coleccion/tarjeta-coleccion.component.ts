import { Component, Input, inject } from '@angular/core';
import { IonicModule } from '@ionic/angular';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Coleccion } from "../../modelos/coleccion.model";
import { ColeccionService } from "../../servicios/coleccion.service";
import { UsuarioService } from "../../servicios/usuario.service";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-tarjeta-coleccion',
  templateUrl: 'tarjeta-coleccion.component.html',
  styleUrls: ['./tarjeta-coleccion.component.scss'],
  standalone: true,
  imports: [IonicModule, RouterModule, CommonModule, FormsModule]
})
export class TarjetaColeccionComponent {
  @Input() coleccion!: Coleccion;
  nombreColeccion: string = '';
  mostrarModalCrearColeccion: boolean = false;

  private router = inject(Router);
  private coleccionService = inject(ColeccionService);
  private usuarioService = inject(UsuarioService);

  idUsuario: number | null = null;

  constructor() {
    this.idUsuario = this.usuarioService.obtenerUsuarioId();
  }

  goToColeccion() {
    if (this.coleccion && this.coleccion.id) {
      console.log('Navegando a la colección con ID:', this.coleccion.id);
      this.router.navigate(['/coleccion', this.coleccion.id]);
    } else {
      console.warn('No se encontró ID en la colección:', this.coleccion);
    }
  }

  abrirModalCrearColeccion() {
    this.mostrarModalCrearColeccion = true;
  }

  cerrarModalCrearColeccion() {
    this.mostrarModalCrearColeccion = false;
  }

  crearColeccion() {
    if (this.nombreColeccion.trim() && this.idUsuario) {
      const nuevaColeccion: Coleccion = {
        id: 0,
        idUsuario: this.idUsuario,
        nombre: this.nombreColeccion
      };

      this.coleccionService.crearColeccion(nuevaColeccion).subscribe((coleccionCreada) => {
        console.log('Colección creada:', coleccionCreada);
        this.cerrarModalCrearColeccion();
        this.router.navigate(['/coleccion', coleccionCreada.id]);
      });
    } else {
      console.warn('El nombre de la colección no puede estar vacío o el usuario no está autenticado');
    }
  }
}
