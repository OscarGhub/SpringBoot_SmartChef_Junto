import { Component, OnInit, inject } from '@angular/core';
import { IonicModule, AlertController } from '@ionic/angular';
import { CommonModule } from '@angular/common';
import { CarritoService } from '../../servicios/carrito.service';
import { ListaCompraIngrediente } from '../../modelos/carrito.model';
import { firstValueFrom } from "rxjs";

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

  listaCompraIngredientes: ListaCompraIngrediente[] = [];
  idLista: number = 1;
  cargando: boolean = false;

  ngOnInit() {
    this.cargarLista();
  }

  async cargarLista() {
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
                this.carritoService.eliminarIngrediente(this.idLista, idIngrediente)
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
