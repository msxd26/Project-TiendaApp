import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { UsuarioRequest, UsuarioResponse, RolRequest, RolResponse } from '../../shared/domain/models/usuario.model';

@Injectable({ providedIn: 'root' })
export class UserService {

  private readonly url = `${environment.apiUrl}/usuario`;
  private readonly rolUrl = `${environment.apiUrl}/rol`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<UsuarioResponse[]> {
    return this.http.get<UsuarioResponse[]>(`${this.url}/`);
  }

  create(usuario: UsuarioRequest): Observable<UsuarioResponse> {
    return this.http.post<UsuarioResponse>(this.url, usuario);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }

  addRole(userId: number, rol: RolRequest): Observable<void> {
    return this.http.post<void>(`${this.url}/${userId}/roles`, rol);
  }

  removeRole(userId: number, rolNombre: string): Observable<void> {
    return this.http.delete<void>(`${this.url}/${userId}/roles/${rolNombre}`);
  }

  getAllRoles(): Observable<RolResponse[]> {
    return this.http.get<RolResponse[]>(this.rolUrl);
  }
}
