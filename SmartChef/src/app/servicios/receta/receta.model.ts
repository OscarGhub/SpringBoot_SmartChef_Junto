export interface Receta {
  id?: number;
  titulo: string;
  descripcion: string;
  tutorial: string;
  tiempo_preparacion: number;
  foto_url: string;
  num_favoritos: number;
}
