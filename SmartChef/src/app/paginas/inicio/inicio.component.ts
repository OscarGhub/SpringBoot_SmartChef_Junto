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
  preferencias: Preferencia[] = [];

  ngOnInit() {
    this.recetaService.getRecetas().subscribe({
      next: (data) => {
        this.recetas = data;
        this.recetasFiltradas = data;
      },
      error: (err) => console.error('Error al cargar recetas', err)
    });
    this.recetaService.getRecetas().subscribe(data => this.recetas = data);
  }

  onSearch(text: string) {
    const texto = text.toLowerCase();
    this.recetasFiltradas = this.recetas.filter(r =>
      r.titulo.toLowerCase().includes(texto) ||
      r.descripcion.toLowerCase().includes(texto)
    );
  }

  filtrar(preferenciaId: number): void {
    this.recetaService.filtrarPorPreferencia(preferenciaId)
      .subscribe(data => this.recetas = data);
  }

  mostrarTodas(): void {
    this.recetaService.getRecetas().subscribe(data => this.recetas = data);
  }
}
