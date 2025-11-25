import { Component, OnInit, inject, Input } from '@angular/core';
import { IonicModule, AlertController } from '@ionic/angular';
import { CommonModule } from '@angular/common';
import { CarritoService } from '../../servicios/carrito.service';
import { ListaCompraIngrediente } from '../../modelos/carrito.model';
import { firstValueFrom } from "rxjs";
import { UsuarioService } from '../../servicios/usuario.service';

@Component({
  selector: 'app-tarjeta-carrito',
  templateUrl: './tarjeta-carrito.component.html',
  styleUrls: ['./tarjeta-carrito.component.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule],
})
export class TarjetaCarritoComponent implements OnInit {
  private carritoService = inject(CarritoService);
  private alertCtrl = inject(AlertController);
  private usuarioService = inject(UsuarioService);

  listaCompraIngredientes: ListaCompraIngrediente[] = [];
  cargando: boolean = false;
  private idLista: number | null = null;

  ngOnInit() {
    this.idLista = this.usuarioService.obtenerUsuarioId();
    this.cargarLista();
  }

  async cargarLista() {
    if (!this.idLista) {
      console.warn('Advertencia: Usuario no logeado o ID de lista no encontrado. No se cargará la lista.');
      return;
    }

    this.cargando = true;
    try {
      this.listaCompraIngredientes = await firstValueFrom(
        this.carritoService.getIngredientes(this.idLista)
      );
    } catch (error) {
      console.error('Error al cargar la lista:', error);
    } finally {
      this.cargando = false;
    }
  }

  async eliminarIngrediente(item: ListaCompraIngrediente) {
    if (!this.idLista) {
      console.error('Error: ID de lista no disponible para eliminar ingrediente.');
      return;
    }

    const idIngrediente = item.ingrediente.id;
    const nombreIngrediente = item.ingrediente.nombre;

    const alert = await this.alertCtrl.create({
      header: 'Confirmar',
      message: `¿Deseas eliminar **${nombreIngrediente}** del carrito?`,
      buttons: [
        { text: 'Cancelar', role: 'cancel' },
        {
          text: 'Eliminar',
          cssClass: 'alerta-danger',
          handler: async () => {
            try {
              await firstValueFrom(
                this.carritoService.eliminarIngrediente(this.idLista!, idIngrediente)
              );

              this.listaCompraIngredientes = this.listaCompraIngredientes.filter(
                i => i.ingrediente.id !== idIngrediente
              );

              const successAlert = await this.alertCtrl.create({
                header: 'Éxito',
                message: `${nombreIngrediente} eliminado.`,
                buttons: ['OK'],
              });
              await successAlert.present();

            } catch (error) {
              console.error('Error al eliminar ingrediente:', error);
              const errorAlert = await this.alertCtrl.create({
                header: 'Error',
                message: `No se pudo eliminar ${nombreIngrediente}.`,
                buttons: ['OK'],
              });
              await errorAlert.present();
            }
          },
        },
      ],
    });

    await alert.present();
  }
}
