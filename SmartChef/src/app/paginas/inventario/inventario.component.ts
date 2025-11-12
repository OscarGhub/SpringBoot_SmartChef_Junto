import { Component, inject, OnInit } from '@angular/core';
import { CabeceraComponent } from "../../componentes/cabecera/cabecera.component";
import { FooterComponent } from "../../componentes/footer/footer.component";
import { IonicModule } from "@ionic/angular";
import { TarjetaInventarioComponent } from "../../componentes/tarjeta-inventario/tarjeta-inventario.component";
import { UsuarioService } from '../../servicios/usuario/usuario.service';
import { Usuario } from '../../servicios/usuario/usuario.model';
import { CommonModule } from '@angular/common';

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
