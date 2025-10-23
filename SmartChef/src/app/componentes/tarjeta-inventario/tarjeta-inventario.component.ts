import { Component } from '@angular/core';
import { IonicModule } from '@ionic/angular';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-tarjeta-inventario',
  templateUrl: './tarjeta-inventario.component.html',
  styleUrls: ['./tarjeta-inventario.component.scss'],
  standalone: true,
  imports: [IonicModule, FormsModule, CommonModule]
})
export class TarjetaInventarioComponent {
  items: string[] = [
  ];

  nuevoItem: string = '';

  agregarItem() {
    if (this.nuevoItem.trim()) {
      this.items.push(this.nuevoItem.trim());
      this.nuevoItem = '';
    }
  }

  eliminarItem(item: string) {
    this.items = this.items.filter(i => i !== item);
  }
}
