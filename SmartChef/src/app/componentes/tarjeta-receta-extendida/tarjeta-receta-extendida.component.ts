import { Component, Input, inject, Output, EventEmitter, OnInit } from '@angular/core';
import { IonicModule } from '@ionic/angular';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Receta } from '../../servicios/receta/receta.model';
import { RecetaService } from '../../servicios/receta/receta.service';
import { UsuarioService } from '../../servicios/usuario/usuario.service';
import { CarritoService } from '../../servicios/carrito/carrito.service';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-tarjeta-receta-extendida',
  templateUrl: './tarjeta-receta-extendida.component.html',
  styleUrls: ['./tarjeta-receta-extendida.component.scss'],
  standalone: true,
  imports: [IonicModule, RouterModule, CommonModule]
})
export class TarjetaRecetaExtendidaComponent implements OnInit {
  @Input() receta?: Receta;
  @Output() cambioFavorito = new EventEmitter<void>();

  private router = inject(Router);
  private recetaService = inject(RecetaService);
  private usuarioService = inject(UsuarioService);
  private carritoService = inject(CarritoService);

  ngOnInit() {
    this.marcarYaGuardada();
  }

  private async marcarYaGuardada() {
    if (!this.receta) return;
    const usuarioData = localStorage.getItem('usuario');
    if (!usuarioData) return;
    const usuario = JSON.parse(usuarioData);
    if (!usuario?.id) return;

    try {
      const yaGuardada = await firstValueFrom(
        this.recetaService.recetaYaGuardada(this.receta.id!, usuario.id)
      );
      this.receta.yaGuardada = yaGuardada;
    } catch (err) {
      console.error('Error comprobando si la receta ya está guardada', err);
    }
  }

  goToReceta() {
    if (this.receta?.id) this.router.navigate(['/receta', this.receta.id]);
  }

  async anadirAlCarrito() {
    if (!this.receta?.id) return;

    const usuarioData = localStorage.getItem('usuario');
    if (!usuarioData) return;

    const usuario = JSON.parse(usuarioData);
    if (!usuario?.id) return;

    const idUsuario: number = usuario.id;

    try {
      let idLista: number;
      const listaGuardada = localStorage.getItem('id_lista');

      if (listaGuardada) {
        idLista = Number(listaGuardada);
      } else {
        const lista: any = await firstValueFrom(this.carritoService.crearListaCompra(usuario.id));
        idLista = lista.id;
        localStorage.setItem('id_lista', idLista.toString());
      }

      await firstValueFrom(this.carritoService.anadirRecetaAlCarrito(idLista, this.receta.id));

      console.log('Receta añadida al carrito con todos sus ingredientes');
    } catch (err) {
      console.error('Error al añadir la receta al carrito:', err);
    }
  }

  onImageError(event: Event) {
    const img = event.target as HTMLImageElement;
    img.src = '../../../assets/images/receta.png';
  }

  async toggleFavorito(event: Event) {
    event.stopPropagation();
    if (!this.receta) return;

    const usuarioData = localStorage.getItem('usuario');
    if (!usuarioData) return;
    const usuario = JSON.parse(usuarioData);
    if (!usuario?.id) return;

    const idUsuario = usuario.id;

    try {
      if (!this.receta.yaGuardada) {
        const recetaActualizada = await firstValueFrom(
          this.recetaService.guardarReceta(this.receta.id!, idUsuario)
        );
        this.receta.yaGuardada = true;
        this.receta.numFavoritos = recetaActualizada.numFavoritos;
      } else {
        const recetaActualizada = await firstValueFrom(
          this.recetaService.quitarRecetaGuardada(this.receta.id!, idUsuario)
        );
        this.receta.yaGuardada = false;
        this.receta.numFavoritos = recetaActualizada.numFavoritos;
      }
    } catch (err) {
      console.error('Error al guardar/quitar receta', err);
    }
  }
}
