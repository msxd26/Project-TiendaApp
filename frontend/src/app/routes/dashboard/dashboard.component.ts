import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { AuthService } from '../../core/services/auth.service';

interface QuickLink {
  label: string;
  icon: string;
  route: string;
  color: string;
  roles: string[];
}

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [RouterLink, MatCardModule, MatIconModule, MatButtonModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent {
  private readonly quickLinks: QuickLink[] = [
    { label: 'Artículos', icon: 'inventory_2', route: '/articles', color: '#1976d2', roles: ['ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR', 'ROLE_ALMACENERO', 'ROLE_CLIENTE'] },
    { label: 'Categorías', icon: 'category', route: '/categories', color: '#388e3c', roles: ['ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR', 'ROLE_ALMACENERO', 'ROLE_CLIENTE'] },
    { label: 'Personas', icon: 'people', route: '/people', color: '#f57c00', roles: ['ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR', 'ROLE_ALMACENERO'] },
    { label: 'Ingresos', icon: 'move_to_inbox', route: '/purchases', color: '#7b1fa2', roles: ['ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_ALMACENERO'] },
    { label: 'Ventas', icon: 'point_of_sale', route: '/sales', color: '#d32f2f', roles: ['ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR'] },
    { label: 'Usuarios', icon: 'manage_accounts', route: '/users', color: '#0097a7', roles: ['ROLE_ADMIN'] },
  ];

  visibleLinks: QuickLink[] = [];

  constructor(public authService: AuthService) {
    const roles = this.authService.userRoles();
    this.visibleLinks = this.quickLinks.filter(l =>
      l.roles.some(r => roles.includes(r))
    );
  }
}
