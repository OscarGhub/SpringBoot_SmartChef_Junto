import { Component, OnInit } from '@angular/core';
import {CabeceraComponent} from "../cabecera/cabecera.component";
import {FooterComponent} from "../footer/footer.component";
import {IonicModule} from "@ionic/angular";
import {SearchBarComponent} from "../search-bar/search-bar.component";
import {TarjetaComponent} from "../tarjeta_receta/tarjeta.component";
import {TarjetaInventarioComponent} from "../tarjeta-inventario/tarjeta-inventario.component";

@Component({
  selector: 'app-inventario',
  templateUrl: './inventario.component.html',
  styleUrls: ['./inventario.component.scss'],
  standalone: true,
  imports: [
    CabeceraComponent,
    FooterComponent,
    IonicModule,
    TarjetaInventarioComponent
  ]
})
export class InventarioComponent  implements OnInit {

  constructor() { }

  ngOnInit() {}

}
