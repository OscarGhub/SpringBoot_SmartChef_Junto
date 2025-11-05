export interface Ingrediente {
  id: number;
  nombre: string;
  descripcion?: string;
  cantidad?: number;
  unidadMedida?: string;
  imagenUrl?: string;
}

export interface ListaCompraIngrediente {
  idLista: number;
  ingrediente: Ingrediente;
  cantidad: number;
}
