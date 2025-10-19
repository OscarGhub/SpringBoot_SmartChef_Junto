// inicio.component.ts
import { Component, OnInit } from '@angular/core';
import { IonicModule } from '@ionic/angular';
import { TarjetaComponent } from '../tarjeta_receta/tarjeta.component';
import { CabeceraComponent } from '../cabecera/cabecera.component';
import { FooterComponent } from '../footer/footer.component';
import { SearchBarComponent } from '../search-bar/search-bar.component';  // <-- Importar aquí

@Component({
  selector: 'app-inicio',
  templateUrl: './inicio.component.html',
  styleUrls: ['./inicio.component.scss'],
  standalone: true,
  imports: [
    IonicModule,
    TarjetaComponent,
    CabeceraComponent,
    FooterComponent,
    SearchBarComponent,
  ]
})
export class InicioComponent implements OnInit {

  constructor() { }

  ngOnInit() {}

  onSearch(text: string) {
    console.log('Texto de búsqueda:', text);
  }
}
