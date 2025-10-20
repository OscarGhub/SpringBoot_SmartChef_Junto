import { Component } from '@angular/core';
import {CabeceraComponent} from "../cabecera/cabecera.component";
import {FooterComponent} from "../footer/footer.component";
import {IonicModule} from "@ionic/angular";
import {TarjetaComponent} from "../tarjeta_receta/tarjeta.component";
import {TarjetaCarritoComponent} from "../tarjeta-carrito/tarjeta-carrito.component";

@Component({
    selector: 'app-carrito',
    templateUrl: './carrito.component.html',
    styleUrls: ['./carrito.component.scss'],
    standalone: true,
  imports: [
    CabeceraComponent,
    FooterComponent,
    IonicModule,
    TarjetaCarritoComponent
  ]
})
export class CarritoComponent   {

  constructor() { }



}
