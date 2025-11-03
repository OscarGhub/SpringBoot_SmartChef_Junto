import { Component, Input, inject, Output, EventEmitter, OnInit } from '@angular/core';
import { IonicModule } from '@ionic/angular';
import { Router, RouterModule } from '@angular/router';
import { Receta } from '../../servicios/receta/receta.model';
import { CommonModule } from '@angular/common';
import { RecetaService } from '../../servicios/receta/receta.service';
import { UsuarioService } from '../../servicios/usuario/usuario.service';
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

  ngOnInit() {
    this.marcarYaGuardada();
  }

  private async marcarYaGuardada() {
    if (!this.receta) return;

    const correo = localStorage.getItem('correo_electronico');
    if (!correo) return;

    const usuario = await firstValueFrom(this.usuarioService.getUsuarioPorCorreo(correo));
    if (!usuario || usuario.id === undefined) return;

    this.recetaService.recetaYaGuardada(this.receta.id!, usuario.id)
      .subscribe(ya => this.receta!.yaGuardada = ya);
  }

  goToReceta() {
    if (this.receta?.id) {
      this.router.navigate(['/receta', this.receta.id]);
    }
  }

  anadirAlCarrito() {
    console.log('Añadir al carrito:', this.receta?.id);
  }

  async onImageError(event: Event) {
    const img = event.target as HTMLImageElement;
    img.src = '../../../assets/images/receta.png';
  }

  async toggleFavorito() {
    if (!this.receta) return;
    const correo = localStorage.getItem('correo_electronico');
    if (!correo) return;
    const usuario = await firstValueFrom(this.usuarioService.getUsuarioPorCorreo(correo));
    if (!usuario || usuario.id === undefined) return;

    try {
      if (this.receta.yaGuardada) {
        const updatedReceta = await firstValueFrom(
          this.recetaService.quitarRecetaGuardada(this.receta.id!, usuario.id)
        );
        this.receta.num_favoritos = updatedReceta.num_favoritos;
        this.receta.yaGuardada = false;
      } else {
        const updatedReceta = await firstValueFrom(
          this.recetaService.guardarReceta(this.receta.id!, usuario.id)
        );
        this.receta.num_favoritos = updatedReceta.num_favoritos;
        this.receta.yaGuardada = true;
      }

      this.cambioFavorito.emit();
    } catch (error) {
      console.error('Error al actualizar favoritos:', error);
    }
  }

}
