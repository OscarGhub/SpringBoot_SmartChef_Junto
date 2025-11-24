import { Component, OnInit, inject, Input } from '@angular/core';
import { IonicModule } from '@ionic/angular';
import { CommonModule } from '@angular/common';
import { InventarioService } from '../../servicios/inventario.service';
import { IngredienteService } from '../../servicios/ingrediente.service';
import { Ingrediente } from '../../modelos/ingrediente.model';
import { InventarioIngredienteItem } from "../../modelos/inventario-ingrediente.model";

@Component({
  selector: 'app-tarjeta-inventario',
  templateUrl: './tarjeta-inventario.component.html',
  styleUrls: ['./tarjeta-inventario.component.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule]
})
export class TarjetaInventarioComponent implements OnInit {

  @Input() usuarioId?: number;

  items: InventarioIngredienteItem[] = [];
  ingredientesDisponibles: Ingrediente[] = [];
  ingredientesSeleccionados: Ingrediente[] = [];
  mostrarSelector = false;

  private inventarioService = inject(InventarioService);
  private ingredienteService = inject(IngredienteService);

  ngOnInit() {
    if (!this.usuarioId) throw new Error('usuarioId no proporcionado');
    this.cargarInventarioBackend();
    this.cargarIngredientesDisponibles();
  }

  private cargarInventarioBackend() {
    if (!this.usuarioId) return;

    this.inventarioService.getInventarioDetalladoPorUsuario(this.usuarioId).subscribe({
      next: (data) => {
        console.log('Inventario DETALLADO recibido del backend:', data);
        this.items = data;
      },
      error: (err) => {
        console.error('Error obteniendo inventario detallado', err);
        this.items = [];
      }
    });
  }

  private cargarIngredientesDisponibles() {
    this.ingredienteService.getIngredientes().subscribe({
      next: (data) => {
        const idsInventario = new Set(this.items.map(item => item.idIngrediente));
        this.ingredientesDisponibles = data.filter(ingrediente => ingrediente != null && !idsInventario.has(ingrediente.id));

        console.log('Ingredientes disponibles cargados y filtrados:', this.ingredientesDisponibles);
      },
      error: () => {
        this.ingredientesDisponibles = [];
        console.error('Error al cargar ingredientes disponibles');
      }
    });
  }

  abrirSelector() {
    this.cargarInventarioBackend();
    this.cargarIngredientesDisponibles();
    this.ingredientesSeleccionados = [];
    this.mostrarSelector = true;
  }

  cerrarSelector() {
    this.mostrarSelector = false;
  }

  agregarIngrediente(ingrediente: Ingrediente) {
    if (ingrediente && !this.ingredientesSeleccionados.some(i => i.id === ingrediente.id)) {
      this.ingredientesSeleccionados.push(ingrediente);
      this.ingredientesDisponibles = this.ingredientesDisponibles.filter(i => i.id !== ingrediente.id);
    }
  }

  eliminarIngrediente(ingrediente: Ingrediente) {
    const index = this.ingredientesSeleccionados.findIndex(i => i.id === ingrediente.id);
    if (index > -1) {
      const removed = this.ingredientesSeleccionados.splice(index, 1)[0];
      this.ingredientesDisponibles.push(removed);
      this.ingredientesDisponibles.sort((a, b) => (a.nombre > b.nombre) ? 1 : -1);
    }
  }

  confirmarSeleccion() {
    if (!this.usuarioId || this.ingredientesSeleccionados.length === 0) {
      this.cerrarSelector();
      return;
    }

    const idInventario = this.items.length > 0 ? this.items[0].idInventario : null;
    if (!idInventario) {
      console.error('No se pudo obtener el ID de inventario. ¿El usuario tiene un inventario creado?');
      this.cerrarSelector();
      return;
    }

    this.ingredientesSeleccionados.forEach((ingrediente) => {
      if (!ingrediente) return;

      const ingredienteExistente = this.items.find(i => i.idIngrediente === ingrediente.id);

      if (ingredienteExistente) {
        const nuevaCantidad = (ingredienteExistente.cantidad ?? 0) + 1;
        this.inventarioService.agregarIngredienteAlInventario(idInventario, ingrediente.id, nuevaCantidad).subscribe({
          next: () => this.cargarInventarioBackend(),
          error: (err) => console.error('Error actualizando cantidad del ingrediente', err)
        });
      } else {
        this.inventarioService.agregarIngredienteAlInventario(idInventario, ingrediente.id, 1).subscribe({
          next: () => this.cargarInventarioBackend(),
          error: (err) => console.error('Error agregando ingrediente al inventario', err)
        });
      }
    });

    this.ingredientesSeleccionados = [];
    this.cerrarSelector();
  }

  eliminarItemBackend(item: InventarioIngredienteItem) {
    if (!item.idInventario || !item.idIngrediente) return;

    this.inventarioService.eliminarIngredienteDelInventario(item.idInventario, item.idIngrediente).subscribe({
      next: () => {
        this.cargarInventarioBackend();
      },
      error: (err) => {
        console.error('Error eliminando item del inventario', err);
      }
    });
  }

}
