import {Observable} from "rxjs";
import {RecetaUso} from "../modelos/receta-uso.model";
import {inject} from "@angular/core";
import {HttpClient} from "@angular/common/http";

export class HistorialRecetaService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/api/historial';

  getRecetasPorDia(): Observable<RecetaUso[]> {
    return this.http.get<RecetaUso[]>(`${this.apiUrl}/receta-dia`);
  }

  getRecetasUltimaSemana(): Observable<RecetaUso[]> {
    return this.http.get<RecetaUso[]>(`${this.apiUrl}/recetas-semanal`);
  }
}
