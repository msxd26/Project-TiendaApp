export interface CategoriaRequest {
  nombre: string;
  descripcion: string;
}

export interface CategoriaResponse {
  idcategoria: number;
  nombre: string;
  descripcion: string;
  estado: boolean;
}
