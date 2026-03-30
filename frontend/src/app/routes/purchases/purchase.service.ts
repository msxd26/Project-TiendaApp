import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { IngresoRequest, IngresoResponse, DetalleIngresoRequest } from '../../shared/domain/models/ingreso.model';
import { Page } from '../../shared/domain/models/page.model';
import { SortType } from '../../shared/domain/enums/sort-type.enum';

@Injectable({ providedIn: 'root' })
export class PurchaseService {

  private readonly url = `${environment.apiUrl}/ingreso`;

  constructor(private http: HttpClient) {}

  getAll(page = 0, size = 10, sortType: SortType = 'NONE'): Observable<Page<IngresoResponse>> {
    const params = new HttpParams()
      .set('page', page).set('size', size).set('sortType', sortType);
    return this.http.get<Page<IngresoResponse>>(this.url, { params });
  }

  getById(id: number): Observable<IngresoResponse> {
    return this.http.get<IngresoResponse>(`${this.url}/${id}`);
  }

  create(ingreso: IngresoRequest): Observable<IngresoResponse> {
    return this.http.post<IngresoResponse>(this.url, ingreso);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }

  addDetalle(idIngreso: number, detalle: DetalleIngresoRequest): Observable<IngresoResponse> {
    return this.http.patch<IngresoResponse>(`${this.url}/${idIngreso}/detalles`, detalle);
  }

  removeDetalle(idIngreso: number, idDetalle: number): Observable<IngresoResponse> {
    return this.http.patch<IngresoResponse>(`${this.url}/${idIngreso}/detalles/${idDetalle}`, {});
  }
}
