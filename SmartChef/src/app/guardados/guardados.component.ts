import { Component, OnInit } from '@angular/core';
import {CabeceraComponent} from "../cabecera/cabecera.component";
import {IonicModule} from "@ionic/angular";
import {SearchBarComponent} from "../search-bar/search-bar.component";
import {TarjetaComponent} from "../tarjeta_receta/tarjeta.component";
import {FooterComponent} from "../footer/footer.component";

@Component({
  selector: 'app-guardados',
  templateUrl: './guardados.component.html',
  styleUrls: ['./guardados.component.scss'],
  standalone: true,
  imports: [
    IonicModule,
    CabeceraComponent,
    TarjetaComponent,
    FooterComponent
  ]
})
export class GuardadosComponent  implements OnInit {

  constructor() { }

  ngOnInit() {}

  onSearch(text: string) {
    console.log('Texto de búsqueda:', text);
  }

}
