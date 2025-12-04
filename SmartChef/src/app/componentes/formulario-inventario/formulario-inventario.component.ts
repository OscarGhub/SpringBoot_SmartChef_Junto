import { Component, OnInit } from '@angular/core';
import {FormsModule} from "@angular/forms";
import {IonicModule} from "@ionic/angular";

@Component({
  selector: 'app-formulario-inventario',
  templateUrl: './formulario-inventario.component.html',
  styleUrls: ['./formulario-inventario.component.scss'],
  standalone: true,
  imports: [
    FormsModule,
    IonicModule
  ]
})
export class FormularioInventarioComponent {

  constructor() { }

}
