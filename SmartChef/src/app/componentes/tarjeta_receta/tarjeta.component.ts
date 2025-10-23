import { Component, Input, inject } from '@angular/core';
import { IonicModule } from '@ionic/angular';
import { Router, RouterModule } from '@angular/router';
import { Receta } from '../../servicios/receta/receta.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-tarjeta-receta',
  templateUrl: './tarjeta.component.html',
  styleUrls: ['./tarjeta.component.scss'],
  standalone: true,
  imports: [IonicModule, RouterModule, CommonModule]
})
export class TarjetaComponent {
  @Input() receta!: Receta;

  private router = inject(Router);

  goToReceta() {
    if (this.receta?.id) {
      this.router.navigate(['/receta', this.receta.id]);
    } else {
      console.warn('Receta o ID no definido');
    }
  }
}
