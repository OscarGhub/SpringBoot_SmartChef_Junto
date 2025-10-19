import { Component, OnInit } from '@angular/core';
import { IonicModule } from '@ionic/angular';
import { FormsModule } from '@angular/forms';
import { UsuarioService, LoginResponse } from '../servicios/usuario/usuario.service';
import { UsuarioLogin } from '../servicios/usuario/usuario.model';

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
export class LoginComponent implements OnInit {

  email: string = '';
  password: string = '';
  rememberMe: boolean = false;

  constructor(private usuarioService: UsuarioService) { }

  ngOnInit() {}

  login() {
    const usuario: UsuarioLogin = {
      correoElectronico: this.email,
      contrasena: this.password
    };

    this.usuarioService.login(usuario).subscribe(
      (res: LoginResponse) => console.log('Login correcto', res),
      (err: any) => console.error('Error al iniciar sesión', err)
    );
  }
}
