// inventario.service.ts
import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { InventarioItem } from './inventario.model';

@Injectable({
  providedIn: 'root',
})
export class InventarioService {

  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/api/inventario';

  getInventarioPorUsuario(idUsuario: number): Observable<InventarioItem[]> {
    return this.http.get<InventarioItem[]>(`${this.apiUrl}/${idUsuario}`);
  }

  crearItem(item: InventarioItem): Observable<InventarioItem> {
    return this.http.post<InventarioItem>(this.apiUrl, item);
  }

  eliminarItem(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
