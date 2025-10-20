import { Component, inject } from '@angular/core';
import { IonicModule } from '@ionic/angular';
import { FormsModule } from '@angular/forms';
import { UsuarioService } from '../servicios/usuario/usuario.service';
import { UsuarioLogin, LoginResponse } from '../servicios/usuario/usuario.model';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  standalone: true,
  imports: [
    IonicModule,
    FormsModule,
  ]
})
export class LoginComponent  {
  private usuarioService = inject(UsuarioService);


  email: string = '';
  password: string = '';
  rememberMe: boolean = false;

  login() {
    const usuario: UsuarioLogin = {
      correo_electronico: this.email,
      contrasena: this.password
    };

    this.usuarioService.loginUsuario(usuario).subscribe(
      (res: LoginResponse) => console.log('Login correcto', res),
      (err: any) => console.error('Error al iniciar sesión', err)
    );
  }
}
