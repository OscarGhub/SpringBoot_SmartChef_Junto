import { Component, OnInit } from '@angular/core';
import { IonicModule } from '@ionic/angular';
import { FormsModule } from '@angular/forms';

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

  constructor() {}

  ngOnInit() {}

  register() {
    if (!this.username || !this.email || !this.password || !this.confirmPassword || !this.birthDate) {
      alert('Por favor, completa todos los campos.');
      return;
    }

    if (this.password !== this.confirmPassword) {
      alert('Las contraseñas no coinciden.');
      return;
    }

    // Aquí iría la lógica para enviar datos al backend o validar más

    alert(`Registro exitoso para ${this.username} con email ${this.email} y fecha de nacimiento ${this.birthDate}`);
  }

}
