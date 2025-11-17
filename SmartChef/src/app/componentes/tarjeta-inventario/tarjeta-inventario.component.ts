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
        this.ingredientesDisponibles = data;
        console.log('Ingredientes disponibles (desde backend):', data);
      },
      error: () => this.ingredientesDisponibles = []
    });
  }

  cerrarSelector() {
    this.mostrarSelector = false;
  }

  agregarItemBackend(idIngrediente: number, cantidad: number = 1) {
    if (!this.usuarioId) return;

    if (this.items.some(i => i.idIngrediente === idIngrediente)) return;

    const fechaActual = new Date().toISOString();

    const payload = {
      id: 0,
      idUsuario: this.usuarioId,
      idIngrediente: idIngrediente,
      cantidad: cantidad,
      fecha_creacion: fechaActual
    };

    this.inventarioService.crearItem(payload).subscribe({
      next: () => this.cargarInventarioBackend(),
      error: (err) => console.error('Error agregando item al inventario', err)
    });

    this.cerrarSelector();
  }

  eliminarItemBackend(item: InventarioItem) {
    if (!item.id) return;

    this.inventarioService.eliminarItem(item.id).subscribe({
      next: () => this.cargarInventarioBackend(),
      error: (err) => console.error('Error eliminando item del inventario', err)
    });
  }
}
