export interface Usuario {
  id?: number;
  nombre?: string;
  correoElectronico: string;
  contrasena: string;
}

export interface UsuarioLogin {
  correoElectronico: string;
  contrasena: string;
}
