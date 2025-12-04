import { Component, Input, inject } from '@angular/core';
import {IonicModule, ModalController} from '@ionic/angular';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Coleccion } from "../../modelos/coleccion.model";
import { ColeccionService } from "../../servicios/coleccion.service";
import { UsuarioService } from "../../servicios/usuario.service";
import {FormsModule} from "@angular/forms";
import {FormularioInventarioComponent} from "../formulario-inventario/formulario-inventario.component";
import {FormularioColeccionComponent} from "../formulario-coleccion/formulario-coleccion.component";

@Component({
  selector: 'app-tarjeta-coleccion',
  templateUrl: 'tarjeta-coleccion.component.html',
  styleUrls: ['./tarjeta-coleccion.component.scss'],
  standalone: true,
  imports: [IonicModule, RouterModule, CommonModule, FormsModule]
})
export class TarjetaColeccionComponent {
  @Input() coleccion!: Coleccion;
  nombreColeccion: string = '';
  mostrarModalCrearColeccion: boolean = false;

  private router = inject(Router);
  private coleccionService = inject(ColeccionService);
  private usuarioService = inject(UsuarioService);
  private modalCtrl = inject(ModalController);

  idUsuario: number | null = null;

  constructor() {
    this.idUsuario = this.usuarioService.obtenerUsuarioId();
  }

  goToColeccion() {
    if (this.coleccion && this.coleccion.id) {
      console.log('Navegando a la colección con ID:', this.coleccion.id);
      this.router.navigate(['/coleccion', this.coleccion.id]);
    } else {
      console.warn('No se encontró ID en la colección:', this.coleccion);
    }
  }

  async abrirModalInsertar() {
    const modal = await this.modalCtrl.create({
      component: FormularioColeccionComponent,
      componentProps: {}
    });

    await modal.present();

    const { data, role } = await modal.onWillDismiss();
    if (role === 'creado' && data) {

    }
  }

}
