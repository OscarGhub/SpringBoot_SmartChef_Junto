import { Component, OnInit } from '@angular/core';
import {IonicModule} from "@ionic/angular";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-formulario-receta',
  templateUrl: './formulario-receta.component.html',
  styleUrls: ['./formulario-receta.component.scss'],
  standalone: true,
  imports: [
    IonicModule,
    FormsModule
  ]
})

export class FormularioRecetaComponent {

  constructor() { }

}
