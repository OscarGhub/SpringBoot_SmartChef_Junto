import { Component, OnInit, inject } from '@angular/core';
import { IonicModule } from '@ionic/angular';
import { CommonModule } from '@angular/common';
import { CarritoService } from '../../servicios/carrito.service';
import { ListaCompraIngrediente } from '../../modelos/carrito.model';
import {Observable} from "rxjs";

@Component({
  selector: 'app-tarjeta-carrito',
  templateUrl: './tarjeta-carrito.component.html',
  styleUrls: ['./tarjeta-carrito.component.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule],
})
export class TarjetaCarritoComponent implements OnInit {
  private carritoService = inject(CarritoService);
  listaCompraIngredientes: ListaCompraIngrediente[] = [];
  idLista: number = 1;

  ngOnInit() {
    this.cargarLista();
  }

  cargarLista() {
    this.carritoService.getIngredientes(this.idLista).subscribe({
      next: data => this.listaCompraIngredientes = data,
      error: err => console.error(err)
    });
  }

  eliminarIngrediente(item: ListaCompraIngrediente) {
    this.carritoService.eliminarIngrediente(this.idLista, item.ingrediente.id).subscribe({
      next: (mensaje) => {
        console.log(mensaje);
        this.cargarLista();
      },
      error: err => console.error('Error al eliminar ingrediente:', err)
    });
  }

}
