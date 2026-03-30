import { TipoComprobante } from '../enums/tipo-comprobante.enum';
import { EstadoTransaccion } from '../enums/estado-transaccion.enum';

export interface DetalleIngresoRequest {
  idarticulo: number;
  cantidad: number;
  precio: number;
}

export interface DetalleIngresoResponse {
  iddetalleIngreso: number;
  idarticulo: number;
  cantidad: number;
  precio: number;
}

export interface IngresoRequest {
  idproveedor: number;
  idusuario: number;
  tipoComprobante: TipoComprobante;
  serieComprobante: string;
  numComprobante: string;
  estado: EstadoTransaccion;
  detalles: DetalleIngresoRequest[];
}

export interface IngresoResponse {
  idingreso: number;
  idproveedor: number;
  idusuario: number;
  tipoComprobante: string;
  serieComprobante: string;
  numComprobante: string;
  fecha: string;
  impuesto: number;
  total: number;
  estado: string;
  detalles: DetalleIngresoResponse[];
}
