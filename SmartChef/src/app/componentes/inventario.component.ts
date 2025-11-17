import { Component, inject, OnInit } from '@angular/core';
import { IonicModule } from "@ionic/angular";
import { CommonModule } from '@angular/common';
import {TarjetaInventarioComponent} from "./tarjeta-inventario/tarjeta-inventario.component";
import {FooterComponent} from "./footer/footer.component";
import {CabeceraComponent} from "./cabecera/cabecera.component";
import {Usuario} from "../modelos/usuario.model";
import {UsuarioService} from "../servicios/usuario.service";

@Component({
  selector: 'app-inventario',
  templateUrl: './inventario.component.html',
  styleUrls: ['./inventario.component.scss'],
  standalone: true,
  imports: [
    CabeceraComponent,
    FooterComponent,
    IonicModule,
    TarjetaInventarioComponent,
    CommonModule
  ]
})
export class InventarioComponent implements OnInit {

  usuario?: Usuario | null;

  private usuarioService = inject(UsuarioService);

  ngOnInit() {
    this.inicializarUsuario();
  }

  private inicializarUsuario() {
    const data = localStorage.getItem('usuario');
    if (!data) return;

    try {
      this.usuario = JSON.parse(data);
    } catch (e) {
      console.error('Error parseando usuario de localStorage', e);
      return;
    }

    const correo = this.usuario?.correoElectronico;
    if (!correo) return;

    this.usuarioService.getUsuarioPorCorreo(correo).subscribe({
      next: (u) => {
        if (u?.id != null) {
          this.usuario = u;
        }
      },
      error: (err) => console.error('Error obteniendo usuario del servidor', err)
    });
  }
}
