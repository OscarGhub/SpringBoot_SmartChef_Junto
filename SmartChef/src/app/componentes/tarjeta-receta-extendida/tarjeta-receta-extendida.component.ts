import { Component, Input, inject } from '@angular/core';
import { IonicModule } from '@ionic/angular';
import { Router, RouterModule } from '@angular/router';
import { Receta } from '../../servicios/receta/receta.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-tarjeta-receta-extendida',
  templateUrl: './tarjeta-receta-extendida.component.html',
  styleUrls: ['./tarjeta-receta-extendida.component.scss'],
  standalone: true,
  imports: [IonicModule, RouterModule, CommonModule]
})
export class TarjetaRecetaExtendidaComponent {
  @Input() receta?: Receta;

  private router = inject(Router);

  goToReceta() {
    if (this.receta?.id) {
      this.router.navigate(['/receta', this.receta.id]);
    } else {
      console.warn('Receta o ID no definido');
    }
  }

  guardarReceta() {
    console.log('Guardar receta:', this.receta?.id);
  }

  anadirAlCarrito() {
    console.log('Añadir al carrito:', this.receta?.id);
  }

  async onImageError(event: Event) {
    const img = event.target as HTMLImageElement;
    img.src = '../../../assets/images/receta.png';
  }
}
