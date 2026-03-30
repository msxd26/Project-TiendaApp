import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { PersonaRequest, PersonaResponse } from '../../shared/domain/models/persona.model';
import { Page } from '../../shared/domain/models/page.model';
import { SortType } from '../../shared/domain/enums/sort-type.enum';

@Injectable({ providedIn: 'root' })
export class PersonService {

  private readonly url = `${environment.apiUrl}/persona`;

  constructor(private http: HttpClient) {}

  getAll(page = 0, size = 10, sortType: SortType = 'NONE'): Observable<Page<PersonaResponse>> {
    const params = new HttpParams()
      .set('page', page).set('size', size).set('sortType', sortType);
    return this.http.get<Page<PersonaResponse>>(this.url, { params });
  }

  getById(id: number): Observable<PersonaResponse> {
    return this.http.get<PersonaResponse>(`${this.url}/${id}`);
  }

  create(persona: PersonaRequest): Observable<PersonaResponse> {
    return this.http.post<PersonaResponse>(this.url, persona);
  }

  update(id: number, persona: PersonaRequest): Observable<PersonaResponse> {
    return this.http.put<PersonaResponse>(`${this.url}/${id}`, persona);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
