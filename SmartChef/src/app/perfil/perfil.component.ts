import { Component } from '@angular/core';
import {CabeceraComponent} from "../cabecera/cabecera.component";
import {FooterComponent} from "../footer/footer.component";
import {IonicModule} from "@ionic/angular";
import {SearchBarComponent} from "../search-bar/search-bar.component";
import {TarjetaComponent} from "../tarjeta_receta/tarjeta.component";
import {TarjetaPerfilComponent} from "../tarjeta-perfil/tarjeta-perfil.component";

@Component({
    selector: 'app-perfil',
    templateUrl: './perfil.component.html',
    styleUrls: ['./perfil.component.scss'],
    standalone: true,
  imports: [
    CabeceraComponent,
    FooterComponent,
    IonicModule,
    TarjetaPerfilComponent,
  ]
})
export class PerfilComponent   {

  constructor() { }



}
