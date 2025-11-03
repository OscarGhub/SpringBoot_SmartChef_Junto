import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CarritoService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/api/carrito';

  crearListaCompra(idUsuario: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/listacompra`, { id_usuario: idUsuario });
  }

  anadirRecetaAlCarrito(idLista: number, idReceta: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/listacompra/${idLista}/receta`, { id_receta: idReceta });
  }
}
