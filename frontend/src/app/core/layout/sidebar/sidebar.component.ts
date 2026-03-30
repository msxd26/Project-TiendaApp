import { Component, computed } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { AuthService } from '../../services/auth.service';

interface NavItem {
  label: string;
  icon: string;
  route: string;
  roles: string[];
}

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, MatListModule, MatIconModule, MatDividerModule],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss'
})
export class SidebarComponent {

  private readonly navItems: NavItem[] = [
    { label: 'Dashboard', icon: 'dashboard', route: '/dashboard', roles: [] },
    { label: 'Artículos', icon: 'inventory_2', route: '/articles', roles: ['ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR', 'ROLE_ALMACENERO', 'ROLE_CLIENTE', 'ROLE_INVITADO'] },
    { label: 'Categorías', icon: 'category', route: '/categories', roles: ['ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR', 'ROLE_ALMACENERO', 'ROLE_CLIENTE', 'ROLE_INVITADO'] },
    { label: 'Personas', icon: 'people', route: '/people', roles: ['ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR', 'ROLE_ALMACENERO'] },
    { label: 'Ingresos', icon: 'move_to_inbox', route: '/purchases', roles: ['ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_ALMACENERO'] },
    { label: 'Ventas', icon: 'point_of_sale', route: '/sales', roles: ['ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR'] },
    { label: 'Usuarios', icon: 'manage_accounts', route: '/users', roles: ['ROLE_ADMIN'] },
  ];

  visibleItems = computed(() => {
    const roles = this.authService.userRoles();
    return this.navItems.filter(item =>
      item.roles.length === 0 || item.roles.some(r => roles.includes(r))
    );
  });

  constructor(private authService: AuthService) {}
}
