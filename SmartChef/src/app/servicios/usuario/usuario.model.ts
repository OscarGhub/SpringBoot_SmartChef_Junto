export interface Usuario {
  id?: number;
  nombre?: string;
  correo_electronico: string;
  contrasena: string;
  fecha_nacimiento: string;
}

export interface UsuarioLogin {
  correo_electronico: string;
  contrasena: string;
}

export interface LoginResponse {
  token: string;
  usuario?: Usuario;
}
