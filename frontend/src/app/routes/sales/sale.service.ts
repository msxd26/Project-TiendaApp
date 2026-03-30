import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { VentaRequest, VentaResponse, DetalleVentaRequest } from '../../shared/domain/models/venta.model';
import { Page } from '../../shared/domain/models/page.model';
import { SortType } from '../../shared/domain/enums/sort-type.enum';

@Injectable({ providedIn: 'root' })
export class SaleService {

  private readonly url = `${environment.apiUrl}/venta`;

  constructor(private http: HttpClient) {}

  getAll(page = 0, size = 10, sortType: SortType = 'NONE'): Observable<Page<VentaResponse>> {
    const params = new HttpParams()
      .set('page', page).set('size', size).set('sortType', sortType);
    return this.http.get<Page<VentaResponse>>(this.url, { params });
  }

  getById(id: number): Observable<VentaResponse> {
    return this.http.get<VentaResponse>(`${this.url}/${id}`);
  }

  create(venta: VentaRequest): Observable<VentaResponse> {
    return this.http.post<VentaResponse>(this.url, venta);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }

  addDetalle(idVenta: number, detalle: DetalleVentaRequest): Observable<VentaResponse> {
    return this.http.patch<VentaResponse>(`${this.url}/${idVenta}/detalles`, detalle);
  }

  removeDetalle(idVenta: number, idDetalle: number): Observable<VentaResponse> {
    return this.http.patch<VentaResponse>(`${this.url}/${idVenta}/detalles/${idDetalle}`, {});
  }
}
