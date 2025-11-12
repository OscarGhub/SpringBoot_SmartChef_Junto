import { Component, Input, inject } from '@angular/core';
import { IonicModule } from '@ionic/angular';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import {Coleccion} from "../../servicios/coleccion/coleccion.model";

@Component({
  selector: 'app-tarjeta-coleccion',
  templateUrl: 'tarjeta-coleccion.component.html',
  styleUrls: ['./tarjeta-coleccion.component.scss'],
  standalone: true,
  imports: [IonicModule, RouterModule, CommonModule]
})
export class TarjetaColeccionComponent {
  @Input() coleccion!: Coleccion;

  private router = inject(Router);

  goToColeccion() {
    const id = this.coleccion.id;

    if (id) {
      console.log('Navegando a:', id);
      this.router.navigate(['/coleccion', id]);
    } else {
      console.warn('No se encontró ID en coleccion:', this.coleccion);
    }
  }
}
