import { Component, OnInit, inject } from '@angular/core';
import { IonicModule } from '@ionic/angular';
import { Router } from '@angular/router';

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

  constructor() { }

  ngOnInit() {}

  goToPerfil() {
    this.router.navigate(['/perfil']);
  }
}
