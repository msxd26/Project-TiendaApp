import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ArticuloRequest, ArticuloResponse } from '../../shared/domain/models/articulo.model';
import { Page } from '../../shared/domain/models/page.model';
import { SortType } from '../../shared/domain/enums/sort-type.enum';

@Injectable({ providedIn: 'root' })
export class ArticleService {

  private readonly url = `${environment.apiUrl}/articulo`;

  constructor(private http: HttpClient) {}

  getAll(page = 0, size = 10, sortType: SortType = 'NONE'): Observable<Page<ArticuloResponse>> {
    const params = new HttpParams()
      .set('page', page)
      .set('size', size)
      .set('sortType', sortType);
    return this.http.get<Page<ArticuloResponse>>(this.url, { params });
  }

  search(nombre: string): Observable<ArticuloResponse[]> {
    return this.http.get<ArticuloResponse[]>(`${this.url}/buscar`, {
      params: new HttpParams().set('nombre', nombre)
    });
  }

  getById(id: number): Observable<ArticuloResponse> {
    return this.http.get<ArticuloResponse>(`${this.url}/${id}`);
  }

  create(articulo: ArticuloRequest): Observable<ArticuloResponse> {
    return this.http.post<ArticuloResponse>(this.url, articulo);
  }

  update(id: number, articulo: ArticuloRequest): Observable<ArticuloResponse> {
    return this.http.put<ArticuloResponse>(`${this.url}/${id}`, articulo);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
