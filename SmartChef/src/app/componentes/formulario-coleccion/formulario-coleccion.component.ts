import { Component, OnInit } from '@angular/core';
import {FormsModule} from "@angular/forms";
import {IonicModule} from "@ionic/angular";

@Component({
  selector: 'app-formulario-coleccion',
  templateUrl: './formulario-coleccion.component.html',
  styleUrls: ['./formulario-coleccion.component.scss'],
  standalone: true,
  imports: [
    FormsModule,
    IonicModule
  ]
})
export class FormularioColeccionComponent {

  constructor() { }


}
