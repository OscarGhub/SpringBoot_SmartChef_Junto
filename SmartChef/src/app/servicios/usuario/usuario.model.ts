export interface Usuario {
  id?: number;
  nombre?: string;
  correo_electronico: string;
  contrasena: string;
  fecha_nacimiento: string;
  foto_url?: string;
}
