import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { RecetaService } from './receta.service';
import { Receta } from './receta.model';

@Component({
  selector: 'app-receta',
  templateUrl: './receta.component.html',
  standalone: true,
  imports: [
    CommonModule,
    IonicModule
  ]
})
export class RecetaComponent implements OnInit {
  private recetaService = inject(RecetaService);

  recetas: Receta[] = [];
  recetasFiltradas: Receta[] = [];
  preferenciasSeleccionadas: number[] = [];

  ngOnInit(): void {
    this.cargarRecetas();
  }

  cargarRecetas(): void {
    this.recetaService.getRecetas().subscribe({
      next: (data) => {
        this.recetas = data;
        this.recetasFiltradas = [...data];
      },
      error: (err) => console.error('Error al cargar recetas', err)
    });
  }
}
