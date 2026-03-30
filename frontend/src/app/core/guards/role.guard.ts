import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { RolNombre } from '../../shared/domain/enums/rol-nombre.enum';

export const roleGuard = (allowedRoles: RolNombre[]): CanActivateFn => {
  return () => {
    const authService = inject(AuthService);
    const router = inject(Router);

    if (authService.hasAnyRole(allowedRoles)) {
      return true;
    }

    router.navigate(['/forbidden']);
    return false;
  };
};
