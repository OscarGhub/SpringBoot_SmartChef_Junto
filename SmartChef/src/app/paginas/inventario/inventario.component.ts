import { Component, OnInit } from '@angular/core';
import {CabeceraComponent} from "../../componentes/cabecera/cabecera.component";
import {FooterComponent} from "../../componentes/footer/footer.component";
import {IonicModule} from "@ionic/angular";
import {SearchBarComponent} from "../../componentes/search-bar/search-bar.component";
import {TarjetaComponent} from "../../componentes/tarjeta_receta/tarjeta.component";
import {TarjetaInventarioComponent} from "../../componentes/tarjeta-inventario/tarjeta-inventario.component";

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
