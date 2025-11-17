import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from 'rxjs';
import { Ingrediente } from '../modelos/ingrediente.model';

@Injectable({
  providedIn: 'root'
})
export class IngredienteService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/api/ingrediente';

  getIngredientes(): Observable<Ingrediente[]> {
    return this.http.get<Ingrediente[]>(`${this.apiUrl}`);
  }
}
