export interface ArticuloRequest {
  idcategoria: number;
  codigo: string;
  nombre: string;
  precioVenta: number;
  stock: number;
  descripcion: string;
}

export interface ArticuloResponse {
  idarticulo: number;
  idcategoria: number;
  codigo: string;
  nombre: string;
  precioVenta: number;
  stock: number;
  descripcion: string;
  estado: boolean;
}
