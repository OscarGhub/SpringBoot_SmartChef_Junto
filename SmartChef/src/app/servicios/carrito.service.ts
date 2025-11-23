import {Observable} from "rxjs";
import {ListaCompraIngrediente} from "../modelos/carrito.model";
import {HttpClient} from "@angular/common/http";
import {inject, Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class CarritoService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/api/carrito';

  eliminarIngrediente(idLista: number, idIngrediente: number): Observable<string> {
    return this.http.delete(`${this.apiUrl}/${idLista}/ingrediente/${idIngrediente}`, { responseType: 'text' });
  }

  getIngredientes(idLista: number): Observable<ListaCompraIngrediente[]> {
    return this.http.get<ListaCompraIngrediente[]>(`${this.apiUrl}/${idLista}/ingredientes`);
  }

  anadirRecetaAlCarrito(idUsuario: number, idReceta: number): Observable<string> {
    return this.http.post(`${this.apiUrl}/usuario/${idUsuario}/receta/${idReceta}`, {}, { responseType: 'text' });
  }

  eliminarRecetaDelCarrito(idUsuario: number, idReceta: number): Observable<string> {
    return this.http.delete(`${this.apiUrl}/usuario/${idUsuario}/receta/${idReceta}`, { responseType: 'text' });
  }

  crearListaCompra(idUsuario: number): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}`, { id_usuario: idUsuario });
  }

  recetaEstaEnCarrito(idUsuario: number, idReceta: number): Observable<boolean> {
    return this.http.get<boolean>(`${this.apiUrl}/usuario/${idUsuario}/receta/${idReceta}/existe`);
  }
}
