import { Component } from '@angular/core';
import {CabeceraComponent} from "../../componentes/cabecera/cabecera.component";
import {IonicModule} from "@ionic/angular";
import {SearchBarComponent} from "../../componentes/search-bar/search-bar.component";
import {TarjetaComponent} from "../../componentes/tarjeta_receta/tarjeta.component";
import {FooterComponent} from "../../componentes/footer/footer.component";

@Component({
  selector: 'app-guardados',
  templateUrl: './guardados.component.html',
  styleUrls: ['./guardados.component.scss'],
  standalone: true,
  imports: [
    IonicModule,
    CabeceraComponent,
    TarjetaComponent,
    FooterComponent,
    TarjetaComponent
  ]
})
export class GuardadosComponent   {

  constructor() { }



  onSearch(text: string) {
    console.log('Texto de búsqueda:', text);
  }

}
