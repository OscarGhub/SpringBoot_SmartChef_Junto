import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { InventarioItem } from '../modelos/inventario.model';

@Injectable({
  providedIn: 'root',
})
export class InventarioService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/api/inventario';

  getInventarioPorUsuario(usuarioId: number): Observable<InventarioItem[]> {
    return this.http.get<InventarioItem[]>(`${this.apiUrl}/usuario/${usuarioId}`);
  }

  crearInventario(param: { id: number; idUsuario: number | null; fecha_creacion: string }): Observable<InventarioItem> {
    if (!param.idUsuario) {
      throw new Error('No se puede crear inventario: idUsuario es null');
    }

    const payload = {
      idInventario: param.id,
      usuarioId: param.idUsuario,
      fechaCreacion: param.fecha_creacion
    };

    return this.http.post<InventarioItem>(this.apiUrl, payload);
  }

  agregarIngredienteAlInventario(idInventario: number, idIngrediente: number, cantidad: number): Observable<any> {
    const url = `${this.apiUrl}/${idInventario}/ingredientes/${idIngrediente}`;
    const payload = { cantidad };

    return this.http.post<any>(url, payload);
  }

  eliminarIngredienteDelInventario(idInventario: number, idIngrediente: number | undefined): Observable<any> {
    const url = `${this.apiUrl}/${idInventario}/ingredientes/${idIngrediente}`;
    return this.http.delete<any>(url);
  }

}
