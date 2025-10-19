import { Component, OnInit, inject } from '@angular/core';
import { IonicModule } from '@ionic/angular';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-tarjeta-receta',
  templateUrl: './tarjeta.component.html',
  styleUrls: ['./tarjeta.component.scss'],
  standalone: true,
  imports: [
    IonicModule,
    RouterModule
  ]
})
export class TarjetaComponent implements OnInit {
  private router = inject(Router);

  constructor() {}

  ngOnInit() {}

  goToReceta() {
    this.router.navigate(['/receta']);
  }
}
