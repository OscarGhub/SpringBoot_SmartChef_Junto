import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Receta } from './receta.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RecetaService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/api/receta';

  getRecetas(): Observable<Receta[]> {
    return this.http.get<Receta[]>(this.apiUrl);
  }

  getRecetaById(id: number): Observable<Receta> {
    return this.http.get<Receta>(`${this.apiUrl}/${id}`);
  }

  filtrarPorPreferencias(preferenciaIds: number[]): Observable<Receta[]> {
    if (!preferenciaIds || preferenciaIds.length === 0) {
      return this.getRecetas();
    }

    let params = new HttpParams();
    preferenciaIds.forEach(id => params = params.append('preferencias', id.toString()));

    return this.http.get<Receta[]>(`${this.apiUrl}/filtrar`, { params });
  }

}
