import { Component, OnInit, inject } from '@angular/core';
import { IonicModule } from '@ionic/angular';
import { TarjetaComponent } from '../../componentes/tarjeta_receta/tarjeta.component';
import { CabeceraComponent } from '../../componentes/cabecera/cabecera.component';
import { FooterComponent } from '../../componentes/footer/footer.component';
import { SearchBarComponent } from '../../componentes/search-bar/search-bar.component';
import { RecetaService } from '../../servicios/receta/receta.service';
import { Receta } from '../../servicios/receta/receta.model';
import { CommonModule } from '@angular/common';

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
    CommonModule,
  ]
})
export class InicioComponent implements OnInit {
  private recetaService = inject(RecetaService);

  recetas: Receta[] = [];
  recetasFiltradas: Receta[] = [];

  ngOnInit() {
    this.recetaService.getRecetas().subscribe({
      next: (data) => {
        this.recetas = data;
        this.recetasFiltradas = data;
      },
      error: (err) => console.error('Error al cargar recetas', err)
    });
  }

  onSearch(text: string) {
    const texto = text.toLowerCase();
    this.recetasFiltradas = this.recetas.filter(r =>
      r.titulo.toLowerCase().includes(texto) ||
      r.descripcion.toLowerCase().includes(texto)
    );
  }
}
