import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';
import { roleGuard } from './core/guards/role.guard';
import { LayoutComponent } from './core/layout/layout.component';

export const routes: Routes = [
  // ── Rutas públicas (sin layout) ──
  {
    path: 'auth/login',
    loadComponent: () => import('./routes/auth/login/login.component').then(m => m.LoginComponent)
  },
  {
    path: 'auth/register',
    loadComponent: () => import('./routes/auth/register/register.component').then(m => m.RegisterComponent)
  },

  // ── Rutas protegidas (con layout) ──
  {
    path: '',
    component: LayoutComponent,
    canActivate: [authGuard],
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      {
        path: 'dashboard',
        loadComponent: () => import('./routes/dashboard/dashboard.component').then(m => m.DashboardComponent)
      },
      {
        path: 'profile',
        loadComponent: () => import('./routes/profile/profile.component').then(m => m.ProfileComponent)
      },
      {
        path: 'articles',
        loadComponent: () => import('./routes/articles/article-list/article-list.component').then(m => m.ArticleListComponent),
        canActivate: [roleGuard(['ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR', 'ROLE_ALMACENERO', 'ROLE_CLIENTE'])]
      },
      {
        path: 'categories',
        loadComponent: () => import('./routes/categories/category-list/category-list.component').then(m => m.CategoryListComponent),
        canActivate: [roleGuard(['ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR', 'ROLE_ALMACENERO', 'ROLE_CLIENTE'])]
      },
      {
        path: 'people',
        loadComponent: () => import('./routes/people/person-list/person-list.component').then(m => m.PersonListComponent),
        canActivate: [roleGuard(['ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR', 'ROLE_ALMACENERO'])]
      },
      {
        path: 'purchases',
        loadComponent: () => import('./routes/purchases/purchase-list/purchase-list.component').then(m => m.PurchaseListComponent),
        canActivate: [roleGuard(['ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_ALMACENERO'])]
      },
      {
        path: 'sales',
        loadComponent: () => import('./routes/sales/sale-list/sale-list.component').then(m => m.SaleListComponent),
        canActivate: [roleGuard(['ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR'])]
      },
      {
        path: 'users',
        loadComponent: () => import('./routes/users/user-list/user-list.component').then(m => m.UserListComponent),
        canActivate: [roleGuard(['ROLE_ADMIN'])]
      },
      {
        path: 'forbidden',
        loadComponent: () => import('./routes/forbidden/forbidden.component').then(m => m.ForbiddenComponent)
      }
    ]
  },

  // ── Wildcard ──
  { path: '**', redirectTo: 'auth/login' }
];
