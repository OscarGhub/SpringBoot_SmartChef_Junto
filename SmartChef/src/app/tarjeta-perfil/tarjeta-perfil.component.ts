import { Component, OnInit, inject } from '@angular/core';
import { IonicModule } from '@ionic/angular';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router'; // Importamos Router

@Component({
  selector: 'app-tarjeta-perfil',
  templateUrl: './tarjeta-perfil.component.html',
  styleUrls: ['./tarjeta-perfil.component.scss'],
  standalone: true,
  imports: [
    IonicModule,
    FormsModule
  ]
})
export class TarjetaPerfilComponent implements OnInit {
  private router = inject(Router);


  nombre = 'Juan Pérez';
  fechaNacimiento = '15/10/1990';
  email = 'juanperezarrobagmail.com';

  editarFecha = false;
  editarEmail = false;

  ngOnInit() {}

  toggleEditar(campo: 'fecha' | 'email') {
    if (campo === 'fecha') this.editarFecha = !this.editarFecha;
    if (campo === 'email') this.editarEmail = !this.editarEmail;
  }

  irAInventario() {
    this.router.navigate(['/inventario']);
  }
}
