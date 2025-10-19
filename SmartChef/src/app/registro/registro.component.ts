import { Component, OnInit } from '@angular/core';
import { IonicModule, AlertController } from '@ionic/angular';
import { FormsModule } from '@angular/forms';
import { UsuarioService } from '../servicios/usuario/usuario.service';
import { Usuario } from '../servicios/usuario/usuario.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-registro',
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.scss'],
  standalone: true,
  imports: [
    IonicModule,
    FormsModule,
  ]
})
export class RegistroComponent implements OnInit {

  username: string = '';
  email: string = '';
  password: string = '';
  confirmPassword: string = '';
  birthDate: string = '';

  constructor(
    private usuarioService: UsuarioService,
    private alertController: AlertController,
    private router: Router
  ) {}

  ngOnInit() {}

  async register() {

    if (!this.username || !this.email || !this.password || !this.confirmPassword || !this.birthDate) {
      const alert = await this.alertController.create({
        header: 'Error',
        message: 'Por favor, completa todos los campos.',
        buttons: ['OK']
      });
      await alert.present();
      return;
    }

    if (this.password !== this.confirmPassword) {
      const alert = await this.alertController.create({
        header: 'Error',
        message: 'Las contraseñas no coinciden.',
        buttons: ['OK']
      });
      await alert.present();
      return;
    }

    const nuevoUsuario: Usuario = {
      id: 0,
      nombre: this.username,
      correoElectronico: this.email,
      contrasena: this.password,
    };

    this.usuarioService.crearUsuario(nuevoUsuario).subscribe({
      next: async (res) => {
        const alert = await this.alertController.create({
          header: 'Registro exitoso',
          message: `Usuario ${this.username} registrado correctamente.`,
          buttons: ['OK']
        });
        await alert.present();

        this.router.navigate(['/login']);
      },
      error: async (err) => {
        const alert = await this.alertController.create({
          header: 'Error',
          message: 'No se pudo registrar el usuario.',
          buttons: ['OK']
        });
        await alert.present();
        console.error(err);
      }
    });
  }

}
