import { Component, OnInit, inject, Input } from '@angular/core';
import { IonicModule } from '@ionic/angular';
import { CommonModule } from '@angular/common';
import { InventarioService } from '../../servicios/inventario.service';
import { IngredienteService } from '../../servicios/ingrediente.service';
import { InventarioItem } from '../../modelos/inventario.model';
import { Ingrediente } from '../../modelos/ingrediente.model';

@Component({
  selector: 'app-tarjeta-inventario',
  templateUrl: './tarjeta-inventario.component.html',
  styleUrls: ['./tarjeta-inventario.component.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule]
})
export class TarjetaInventarioComponent implements OnInit {

  @Input() usuarioId?: number;

  items: InventarioItem[] = [];
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

    this.inventarioService.getInventarioPorUsuario(this.usuarioId).subscribe({
      next: (data) => {
        console.log('Inventario recibido del backend:', data);
        this.items = data;
      },
      error: (err) => {
        if (err.status === 404) {
          console.log('No hay inventario para este usuario. Inicializando vacío.');
          this.items = [];
        } else {
          console.error('Error obteniendo inventario', err);
          this.items = [];
        }
      }
    });
  }

  private cargarIngredientesDisponibles() {
    this.ingredienteService.getIngredientes().subscribe({
      next: (data) => {
        this.ingredientesDisponibles = data.filter(ingrediente => ingrediente != null);
        console.log('Ingredientes disponibles cargados y filtrados:', this.ingredientesDisponibles);
      },
      error: () => {
        this.ingredientesDisponibles = [];
        console.error('Error al cargar ingredientes disponibles');
      }
    });
  }

  abrirSelector() {
    this.cargarIngredientesDisponibles();
    console.log('Ingredientes disponibles al abrir modal:', this.ingredientesDisponibles);
    console.log('Ingredientes seleccionados al abrir modal:', this.ingredientesSeleccionados);
    this.mostrarSelector = true;
  }

  cerrarSelector() {
    this.mostrarSelector = false;
  }

  agregarIngrediente(ingrediente: Ingrediente) {
    if (ingrediente && !this.ingredientesSeleccionados.includes(ingrediente)) {
      this.ingredientesSeleccionados.push(ingrediente);
    }
  }

  eliminarIngrediente(ingrediente: Ingrediente) {
    const index = this.ingredientesSeleccionados.indexOf(ingrediente);
    if (index > -1) {
      this.ingredientesSeleccionados.splice(index, 1);
    }
  }

  confirmarSeleccion() {
    if (!this.usuarioId) return;

    this.ingredientesSeleccionados.forEach((ingrediente) => {
      if (!ingrediente) return;

      const idInventario = this.items.length > 0 ? this.items[0].id : null;
      if (!idInventario) return;

      const ingredienteExistente = this.items.find(i => i.idIngrediente === ingrediente.id);

      if (ingredienteExistente) {
        const nuevaCantidad = (ingredienteExistente?.cantidad ?? 0) + 1;
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

    this.cerrarSelector();
  }

  eliminarItemBackend(item: InventarioItem) {
    if (!item.id || !item.idIngrediente) return;

    this.inventarioService.eliminarIngredienteDelInventario(item.id, item.idIngrediente).subscribe({
      next: () => {
        this.cargarInventarioBackend();
      },
      error: (err) => {
        console.error('Error eliminando item del inventario', err);
      }
    });
  }
}
