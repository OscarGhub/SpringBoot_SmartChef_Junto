import { Component, OnInit, inject } from '@angular/core';
import { IonicModule } from '@ionic/angular';
import { Router } from '@angular/router';
import { UsuarioService } from 'src/app/servicios/usuario/usuario.service';

@Component({
  selector: 'app-cabecera',
  templateUrl: './cabecera.component.html',
  styleUrls: ['./cabecera.component.scss'],
  standalone: true,
  imports: [
    IonicModule
  ]
})
export class CabeceraComponent implements OnInit {

  private router = inject(Router);
  private usuarioService = inject(UsuarioService);

  fotoPerfilUrl: string = '../../../assets/images/perfil.webp';

  constructor() { }

  ngOnInit() {
    const correo = localStorage.getItem('correo_electronico');
    if (correo) {
      this.usuarioService.getUsuarioPorCorreo(correo).subscribe(usuario => {
        if (usuario && usuario.foto_url) {
          this.fotoPerfilUrl = usuario.foto_url;
        }
      });
    }
  }

  goToPerfil() {
    this.router.navigate(['/perfil']);
  }
}
