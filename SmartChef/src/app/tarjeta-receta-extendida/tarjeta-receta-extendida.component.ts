import { Component, OnInit, inject } from '@angular/core';
import { IonicModule } from '@ionic/angular';
import { Router } from '@angular/router';

@Component({
  selector: 'app-tarjeta-receta-extendida',
  templateUrl: './tarjeta-receta-extendida.component.html',
  styleUrls: ['./tarjeta-receta-extendida.component.scss'],
  standalone: true,
  imports: [
    IonicModule
  ]
})
export class TarjetaRecetaExtendidaComponent implements OnInit {

  private router = inject(Router);

  ngOnInit() {

  }

  compartirReceta() {
    console.log('Receta compartida');
    // Aquí se puede integrar la funcionalidad de compartir en redes sociales,
  }

  guardarReceta() {
    console.log('Receta guardada');
    // Aquí agregar la lógica de guardar la receta en un almacenamiento local o backend
  }

  anadirAlCarrito() {
    console.log('Receta añadida al carrito');
    // Aquí se agrega la lógica para añadir la receta al carrito de compras
  }

}
