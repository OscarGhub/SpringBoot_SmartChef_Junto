import { Component, Input, inject, Output, EventEmitter, OnInit } from '@angular/core';
import { IonicModule } from '@ionic/angular';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Receta } from '../../modelos/receta.model';
import { RecetaService } from '../../servicios/receta.service';
import { CarritoService } from '../../servicios/carrito.service';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-tarjeta-receta-extendida',
  templateUrl: './tarjeta-receta-extendida.component.html',
  styleUrls: ['./tarjeta-receta-extendida.component.scss'],
  standalone: true,
  imports: [IonicModule, RouterModule, CommonModule]
})
export class TarjetaRecetaExtendidaComponent implements OnInit {
  @Input() receta?: Receta & { enCarrito?: boolean };
  @Output() cambioFavorito = new EventEmitter<void>();

  private router = inject(Router);
  private recetaService = inject(RecetaService);
  private carritoService = inject(CarritoService);

  private usuario?: { id: number };

  ngOnInit() {
    this.usuario = this.getUsuario();
    if (!this.usuario || !this.receta) return;

    this.marcarYaGuardada();
    this.marcarEnCarrito();
  }

  private getUsuario() {
    const usuarioData = localStorage.getItem('usuario');
    if (!usuarioData) return undefined;
    try {
      return JSON.parse(usuarioData);
    } catch {
      return undefined;
    }
  }

  private async marcarYaGuardada() {
    if (!this.receta || !this.usuario) return;
    try {
      const yaGuardada = await firstValueFrom(
        this.recetaService.recetaYaGuardada(this.receta.id!, this.usuario.id)
      );
      this.receta.yaGuardada = yaGuardada;
    } catch (err) {
      console.error('Error comprobando si la receta ya está guardada', err);
    }
  }

  private async marcarEnCarrito() {
    if (!this.receta || !this.usuario) return;

    try {
      const listaId = await this.obtenerListaCompra(this.usuario.id);
      const ingredientes = await firstValueFrom(this.carritoService.getIngredientes(listaId));

      this.receta.enCarrito = ingredientes.some(ing => ing.recetaId === this.receta!.id);

    } catch (err) {
      console.error('Error comprobando si la receta está en el carrito', err);
      this.receta.enCarrito = false;
    }
  }

  private async obtenerListaCompra(idUsuario: number): Promise<number> {
    const listaGuardada = localStorage.getItem('id_lista');
    if (listaGuardada) return Number(listaGuardada);

    const lista = await firstValueFrom(this.carritoService.crearListaCompra(idUsuario));
    localStorage.setItem('id_lista', lista.id);
    return lista.id;
  }

  async toggleCarrito(event: Event, receta: Receta) {
    event.stopPropagation();
    if (!receta || !this.usuario) return;

    const idUsuario = this.usuario.id;

    try {
      if (receta.enCarrito) {
        await firstValueFrom(this.carritoService.eliminarRecetaDelCarrito(idUsuario, receta.id!));
        receta.enCarrito = false;
      } else {
        await firstValueFrom(this.carritoService.anadirRecetaAlCarrito(idUsuario, receta.id!));
        receta.enCarrito = true;
      }
    } catch (err) {
      console.error('Error al actualizar la receta en el carrito', err);
    }
  }

  async toggleFavorito(event: Event) {
    event.stopPropagation();
    if (!this.receta || !this.usuario) return;

    try {
      if (!this.receta.yaGuardada) {
        const recetaActualizada = await firstValueFrom(
          this.recetaService.guardarReceta(this.receta.id!, this.usuario.id)
        );
        this.receta.yaGuardada = true;
        this.receta.numFavoritos = recetaActualizada.numFavoritos;
      } else {
        const recetaActualizada = await firstValueFrom(
          this.recetaService.quitarRecetaGuardada(this.receta.id!, this.usuario.id)
        );
        this.receta.yaGuardada = false;
        this.receta.numFavoritos = recetaActualizada.numFavoritos;
      }
      this.cambioFavorito.emit();
    } catch (err) {
      console.error('Error al guardar/quitar receta', err);
    }
  }

  goToReceta() {
    if (this.receta?.id) this.router.navigate(['/receta', this.receta.id]);
  }

  onImageError(event: Event) {
    const img = event.target as HTMLImageElement;
    img.src = '../../../assets/images/receta.png';
  }
}
