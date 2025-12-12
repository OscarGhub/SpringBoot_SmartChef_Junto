import {Component, inject, Input} from "@angular/core";
import {IonicModule, ModalController} from "@ionic/angular";
import {FormsModule, NgForm} from "@angular/forms";
import {RecetaService} from "../../servicios/receta.service";
import {Receta} from "../../modelos/receta.model";


interface NuevaRecetaForm {
  titulo: string;
  descripcion: string;
  tiempoPreparacion: number | null;
  img: string;
  pasos: string;
}

@Component({
  selector: 'app-formulario-receta',
  templateUrl: './formulario-receta.component.html',
  styleUrls: ['./formulario-receta.component.scss'],
  standalone: true,
  imports: [
    IonicModule,
    FormsModule
  ]
})
export class FormularioRecetaComponent {
  private modalCtrl = inject(ModalController);
  private recetaService = inject(RecetaService);

  @Input() usuarioId: number | null = null;

  nuevaReceta: NuevaRecetaForm = {
    titulo: '',
    descripcion: '',
    tiempoPreparacion: null,
    img: '',
    pasos: ''
  };

  constructor() { }

  cancelar() {
    this.modalCtrl.dismiss(null, 'cancel');
  }

  guardarReceta(form: NgForm) {
    if (form.invalid || !this.usuarioId) {
      console.error('Formulario inválido o usuario no identificado.');
      return;
    }

    const recetaRequest = {
      titulo: this.nuevaReceta.titulo,
      descripcion: this.nuevaReceta.descripcion,
      tutorial: this.nuevaReceta.pasos,
      tiempoPreparacion: this.nuevaReceta.tiempoPreparacion,
      fotoUrl: this.nuevaReceta.img,
    };

    this.recetaService.crearReceta(recetaRequest).subscribe({
      next: (recetaGuardada: Receta) => {
        console.log('Receta guardada:', recetaGuardada);
        this.modalCtrl.dismiss(recetaGuardada, 'creado');
      },
      error: (err) => {
        console.error('Error al guardar la receta:', err);
      }
    });
  }

}
