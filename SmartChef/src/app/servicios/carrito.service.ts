import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ListaCompraIngrediente } from '../modelos/carrito.model';

@Injectable({
  providedIn: 'root'
})
export class CarritoService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/api/carrito';

  crearListaCompra(idUsuario: number): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/listacompra`, { id_usuario: idUsuario });
  }

  eliminarIngrediente(idLista: number, idIngrediente: number): Observable<string> {
    return this.http.delete(`${this.apiUrl}/listacompra/${idLista}/ingrediente/${idIngrediente}`, { responseType: 'text' });
  }

  getIngredientes(idLista: number): Observable<ListaCompraIngrediente[]> {
    return this.http.get<ListaCompraIngrediente[]>(`${this.apiUrl}/listacompra/${idLista}/ingredientes`);
  }

  anadirRecetaAlCarrito(idUsuario: number, idReceta: number): Observable<string> {
    return this.http.post(`${this.apiUrl}/listacompra/usuario/${idUsuario}/receta/${idReceta}`, {}, { responseType: 'text' });
  }

  eliminarRecetaDelCarrito(idUsuario: number, idReceta: number): Observable<string> {
    return this.http.delete(`${this.apiUrl}/listacompra/usuario/${idUsuario}/receta/${idReceta}`, { responseType: 'text' });
  }
}
