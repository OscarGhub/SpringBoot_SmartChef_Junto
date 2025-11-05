import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable, of } from 'rxjs';
import { Usuario } from './usuario.model';

@Injectable({
  providedIn: 'root',
})
export class UsuarioService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/api/usuario';

  getUsuarios(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(this.apiUrl);
  }

  crearUsuario(usuario: Usuario): Observable<Usuario> {
    return this.http.post<Usuario>(this.apiUrl, usuario);
  }

  getUsuarioPorCorreo(correo: string): Observable<Usuario | null> {
    return this.http.get<Usuario>(`${this.apiUrl}/correo/${correo}`);
  }

  actualizarUsuario(id: number, payload: any) {
    return this.http.put<Usuario>(`http://localhost:8080/api/usuario/${id}`, payload);
  }

  actualizarFoto(id: number, formData: FormData): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}/foto`, formData);
  }
}
