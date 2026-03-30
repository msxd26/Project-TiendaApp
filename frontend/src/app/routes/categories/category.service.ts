import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { CategoriaRequest, CategoriaResponse } from '../../shared/domain/models/categoria.model';
import { Page } from '../../shared/domain/models/page.model';
import { SortType } from '../../shared/domain/enums/sort-type.enum';

@Injectable({ providedIn: 'root' })
export class CategoryService {

  private readonly url = `${environment.apiUrl}/categoria`;

  constructor(private http: HttpClient) {}

  getAll(page = 0, size = 10, sortType: SortType = 'NONE'): Observable<Page<CategoriaResponse>> {
    const params = new HttpParams()
      .set('page', page).set('size', size).set('sortType', sortType);
    return this.http.get<Page<CategoriaResponse>>(this.url, { params });
  }

  search(nombre: string): Observable<CategoriaResponse[]> {
    return this.http.get<CategoriaResponse[]>(`${this.url}/buscar`, {
      params: new HttpParams().set('nombre', nombre)
    });
  }

  getById(id: number): Observable<CategoriaResponse> {
    return this.http.get<CategoriaResponse>(`${this.url}/${id}`);
  }

  create(cat: CategoriaRequest): Observable<CategoriaResponse> {
    return this.http.post<CategoriaResponse>(this.url, cat);
  }

  update(id: number, cat: CategoriaRequest): Observable<CategoriaResponse> {
    return this.http.put<CategoriaResponse>(`${this.url}/${id}`, cat);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
