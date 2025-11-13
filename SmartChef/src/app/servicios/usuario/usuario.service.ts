import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { Usuario } from './usuario.model';
import { tap, catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class UsuarioService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/api/usuario';
  private usuarioKey = 'usuarioActual';

  getUsuarios(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(this.apiUrl);
  }

  crearUsuario(usuario: Usuario): Observable<Usuario> {
    return this.http.post<Usuario>(this.apiUrl, usuario).pipe(
      tap(u => this.guardarUsuario(u))
    );
  }

  getUsuarioPorCorreo(correo: string): Observable<Usuario | null> {
    return this.http.get<Usuario>(`${this.apiUrl}/correo/${correo}`).pipe(
      tap(u => this.guardarUsuario(u)),
      catchError(err => {
        console.error('Usuario no encontrado', err);
        return of(null);
      })
    );
  }

  actualizarUsuario(id: number, payload: any): Observable<Usuario> {
    return this.http.put<Usuario>(`${this.apiUrl}/${id}`, payload).pipe(
      tap(u => this.guardarUsuario(u))
    );
  }

  actualizarFoto(id: number, formData: FormData): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}/foto`, formData);
  }

  actualizarCorreo(id: number, correoElectronico: string): Observable<Usuario> {
    return this.http.put<Usuario>(`${this.apiUrl}/${id}`, { correoElectronico }).pipe(
      tap(u => this.guardarUsuario(u))
    );
  }

  guardarUsuario(usuario: Usuario) {
    localStorage.setItem(this.usuarioKey, JSON.stringify(usuario));
  }

  obtenerUsuario(): Usuario | null {
    const usuario = localStorage.getItem(this.usuarioKey);
    return usuario ? JSON.parse(usuario) : null;
  }

  obtenerUsuarioId(): number | null {
    const usuario = this.obtenerUsuario();
    return usuario?.id ?? null;
  }

  logout() {
    localStorage.removeItem(this.usuarioKey);
  }
}
