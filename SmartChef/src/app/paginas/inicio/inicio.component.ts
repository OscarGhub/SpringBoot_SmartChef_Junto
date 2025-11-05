import { Component, OnInit, inject } from '@angular/core';
import { IonicModule } from '@ionic/angular';
import { TarjetaComponent } from '../../componentes/tarjeta_receta/tarjeta.component';
import { CabeceraComponent } from '../../componentes/cabecera/cabecera.component';
import { FooterComponent } from '../../componentes/footer/footer.component';
import { SearchBarComponent } from '../../componentes/search-bar/search-bar.component';
import { RecetaService } from '../../servicios/receta/receta.service';
import { Receta } from '../../servicios/receta/receta.model';
import { CommonModule } from '@angular/common';
import {Preferencia} from "../../servicios/preferencia/preferencia.model";
import { PreferenciaService } from '../../servicios/preferencia/preferencia.service';

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
  private preferenciaService = inject(PreferenciaService);

  recetas: Receta[] = [];
  recetasFiltradas: Receta[] = [];
  preferencias: Preferencia[] = [];
  preferenciasSeleccionadas: number[] = [];
  textoBusqueda: string = '';

  ngOnInit() {
    this.recetaService.getRecetas().subscribe({
      next: (data) => {
        this.recetas = data;
        this.recetasFiltradas = data;
      },
      error: (err) => console.error('Error al cargar recetas', err)
    });

    this.preferenciaService.getPreferencias().subscribe({
      next: (data) => this.preferencias = data,
      error: (err) => console.error('Error al cargar preferencias', err)
    });
  }

  onSearch(text: string) {
    this.textoBusqueda = text.toLowerCase();
    this.aplicarFiltros();
  }

  filtrar(preferenciaIds: number[]): void {
    this.preferenciasSeleccionadas = preferenciaIds;

    this.recetaService.filtrarPorPreferencias(preferenciaIds).subscribe({
      next: (data) => {
        this.recetasFiltradas = data;
      },
      error: (err) => console.error('Error al filtrar recetas', err)
    });
  }

  private aplicarFiltros(): void {
    this.recetasFiltradas = this.recetas.filter(r => {
      const coincidePreferencia =
        this.preferenciasSeleccionadas.length === 0 ||
        r.preferencias.some(p => p.id !== undefined && this.preferenciasSeleccionadas.includes(p.id));

      const coincideTexto =
        r.titulo.toLowerCase().includes(this.textoBusqueda) ||
        r.descripcion.toLowerCase().includes(this.textoBusqueda);

      return coincidePreferencia && coincideTexto;
    });
  }

}
