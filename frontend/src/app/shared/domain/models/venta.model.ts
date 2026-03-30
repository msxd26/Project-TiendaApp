import { TipoComprobante } from '../enums/tipo-comprobante.enum';
import { EstadoTransaccion } from '../enums/estado-transaccion.enum';

export interface DetalleVentaRequest {
  idarticulo: number;
  cantidad: number;
}

export interface DetalleVentaResponse {
  iddetalleVenta: number;
  idarticulo: number;
  cantidad: number;
  precio: number;
  descuento: number;
}

export interface VentaRequest {
  idcliente: number;
  idusuario: number;
  tipoComprobante: TipoComprobante;
  serieComprobante: string;
  numComprobante: string;
  estado: EstadoTransaccion;
  detalles: DetalleVentaRequest[];
}

export interface VentaResponse {
  idventa: number;
  idcliente: number;
  idusuario: number;
  tipoComprobante: string;
  serieComprobante: string;
  numComprobante: string;
  fechaHora: string;
  impuesto: number;
  total: number;
  estado: string;
  detalles: DetalleVentaResponse[];
}
