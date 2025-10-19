import { Component, OnInit } from '@angular/core';
import {CabeceraComponent} from "../cabecera/cabecera.component";
import {FooterComponent} from "../footer/footer.component";
import {IonicModule} from "@ionic/angular";
import {SearchBarComponent} from "../search-bar/search-bar.component";
import {TarjetaComponent} from "../tarjeta_receta/tarjeta.component";
import {TarjetaRecetaExtendidaComponent} from "../tarjeta-receta-extendida/tarjeta-receta-extendida.component";

@Component({
  selector: 'app-ver-receta',
  templateUrl: './ver-receta.component.html',
  styleUrls: ['./ver-receta.component.scss'],
  standalone: true,
  imports: [
    CabeceraComponent,
    FooterComponent,
    IonicModule,
    TarjetaRecetaExtendidaComponent
  ]
})
export class VerRecetaComponent  implements OnInit {

  constructor() { }

  ngOnInit() {}

}
