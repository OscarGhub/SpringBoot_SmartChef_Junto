import { Component, inject } from '@angular/core';
import { IonicModule } from '@ionic/angular';
import { Router } from '@angular/router';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss'],
  standalone: true,
  imports: [IonicModule]
})
export class FooterComponent {

  private router = inject(Router);

  goToExplorar() {
    this.router.navigate(['/inicio']);
  }

  goToGuardar() {
    this.router.navigate(['/guardados']);
  }

  goToCarrito() {
    this.router.navigate(['/carrito']);
  }
}
