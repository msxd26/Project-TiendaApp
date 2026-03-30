import { Injectable, signal, computed } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { LoginRequest, LoginResponse, JwtPayload } from '../../shared/domain/models/auth.model';
import { UsuarioResponse } from '../../shared/domain/models/usuario.model';
import { RolNombre } from '../../shared/domain/enums/rol-nombre.enum';

@Injectable({ providedIn: 'root' })
export class AuthService {

  private readonly TOKEN_KEY = 'auth_token';
  private readonly apiUrl = environment.apiUrl;

  private _isLoggedIn = signal(this.hasValidToken());
  private _userRoles = signal<string[]>(this.extractRolesFromToken());

  isLoggedIn = this._isLoggedIn.asReadonly();
  userRoles = this._userRoles.asReadonly();

  userEmail = computed(() => {
    const token = this.getToken();
    if (!token) return '';
    const payload = this.decodeToken(token);
    return payload?.sub ?? '';
  });

  constructor(
    private http: HttpClient,
    private router: Router
  ) {}

  login(credentials: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, credentials).pipe(
      tap(response => {
        localStorage.setItem(this.TOKEN_KEY, response.token);
        this._isLoggedIn.set(true);
        this._userRoles.set(this.extractRolesFromToken());
      })
    );
  }

  register(usuario: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/usuario/registrar`, usuario);
  }

  logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    this._isLoggedIn.set(false);
    this._userRoles.set([]);
    this.router.navigate(['/auth/login']);
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  getMyProfile(): Observable<UsuarioResponse> {
    return this.http.get<UsuarioResponse>(`${this.apiUrl}/usuario/me`);
  }

  hasRole(role: RolNombre): boolean {
    return this._userRoles().includes(role);
  }

  hasAnyRole(roles: RolNombre[]): boolean {
    return roles.some(role => this._userRoles().includes(role));
  }

  private hasValidToken(): boolean {
    const token = this.getToken();
    if (!token) return false;
    const payload = this.decodeToken(token);
    if (!payload) return false;
    return payload.exp * 1000 > Date.now();
  }

  private extractRolesFromToken(): string[] {
    const token = this.getToken();
    if (!token) return [];
    const payload = this.decodeToken(token);
    if (!payload?.authorities) return [];
    try {
      const authorities: { authority: string }[] = JSON.parse(payload.authorities);
      return authorities.map(a => a.authority);
    } catch {
      return [];
    }
  }

  private decodeToken(token: string): JwtPayload | null {
    try {
      const payload = token.split('.')[1];
      const decoded = atob(payload);
      return JSON.parse(decoded);
    } catch {
      return null;
    }
  }
}
