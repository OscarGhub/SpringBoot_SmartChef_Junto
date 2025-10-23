import { Component, OnInit } from '@angular/core';
import {IonButton} from "@ionic/angular/standalone";
import {IonicModule} from "@ionic/angular";

@Component({
  selector: 'app-tarjeta-carrito',
  templateUrl: './tarjeta-carrito.component.html',
  styleUrls: ['./tarjeta-carrito.component.scss'],
  standalone: true,
  imports: [
    IonicModule,
  ]
})
export class TarjetaCarritoComponent  implements OnInit {

  constructor() { }

  ngOnInit() {}

}
