import * as bcrypt from 'bcryptjs';

export class UsuarioHelper {
  static validarCampos(usuario: any, confirmar_contrasena: string): string | null {
    if (
      !usuario.nombre?.trim() ||
      !usuario.correo_electronico?.trim() ||
      !usuario.contrasena?.trim() ||
      !confirmar_contrasena?.trim() ||
      !usuario.fecha_nacimiento?.trim()
    ) {
      return 'Por favor completa todos los campos antes de continuar.';
    }

    if (usuario.contrasena !== confirmar_contrasena) {
      return 'Las contraseñas deben coincidir.';
    }

    return null;
  }

  static hashearContrasena(contrasena: string): string {
    const salt = bcrypt.genSaltSync(10);
    return bcrypt.hashSync(contrasena, salt);
  }
}
