import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { HttpErrorResponse } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class NotificationService {

  constructor(private snackBar: MatSnackBar) {}

  success(message: string): void {
    this.snackBar.open(message, 'Cerrar', {
      duration: 3000,
      panelClass: ['snackbar-success'],
      horizontalPosition: 'end',
      verticalPosition: 'top'
    });
  }

  error(message: string): void {
    this.snackBar.open(message, 'Cerrar', {
      duration: 5000,
      panelClass: ['snackbar-error'],
      horizontalPosition: 'end',
      verticalPosition: 'top'
    });
  }

  info(message: string): void {
    this.snackBar.open(message, 'Cerrar', {
      duration: 3000,
      horizontalPosition: 'end',
      verticalPosition: 'top'
    });
  }

  /**
   * Extrae un mensaje legible de un HttpErrorResponse del backend.
   * Soporta múltiples formatos de error del backend Spring Boot:
   * - { message: "..." }
   * - { error: "...", message: "..." }
   * - { errors: ["..."] }  (validación)
   * - String plano
   * - Errores de red (status 0)
   */
  handleHttpError(err: HttpErrorResponse, fallback: string = 'Ha ocurrido un error inesperado'): void {
    const message = this.extractErrorMessage(err, fallback);
    this.error(message);
  }

  /**
   * Extrae el mensaje de error sin mostrarlo.
   * Útil cuando se necesita el texto para lógica adicional.
   */
  extractErrorMessage(err: HttpErrorResponse, fallback: string = 'Ha ocurrido un error inesperado'): string {
    // Error de red / sin conexión
    if (err.status === 0) {
      return 'No se pudo conectar con el servidor. Verifica tu conexión.';
    }

    // Timeout
    if (err.status === 408 || err.statusText === 'Unknown Error') {
      return 'La solicitud tardó demasiado. Inténtalo de nuevo.';
    }

    const body = err.error;

    // Sin cuerpo de error
    if (!body) {
      return this.getMessageByStatus(err.status, fallback);
    }

    // Body es string directo
    if (typeof body === 'string') {
      return this.cleanMessage(body);
    }

    // Spring Boot: { message: "..." }
    if (body.message && typeof body.message === 'string') {
      return this.cleanMessage(body.message);
    }

    // Spring Boot: { error: "...", message: "...", path: "..." }
    if (body.error && typeof body.error === 'string' && !body.message) {
      return this.cleanMessage(body.error);
    }

    // Validación: { errors: ["campo X es requerido", ...] }
    if (Array.isArray(body.errors) && body.errors.length > 0) {
      return body.errors.map((e: any) =>
        typeof e === 'string' ? e : (e.defaultMessage || e.message || JSON.stringify(e))
      ).join('. ');
    }

    // Spring Boot validation: lista de fieldErrors
    if (Array.isArray(body.fieldErrors)) {
      return body.fieldErrors.map((e: any) => `${e.field}: ${e.message}`).join('. ');
    }

    // Fallback por status HTTP
    return this.getMessageByStatus(err.status, fallback);
  }

  /**
   * Limpia mensajes de error de Java/Spring que pueden incluir
   * nombres de clase, stack traces, etc.
   */
  private cleanMessage(raw: string): string {
    // Si contiene stack trace o class name, tomar solo la parte legible
    if (raw.includes('Exception:')) {
      const parts = raw.split('Exception:');
      const lastPart = parts[parts.length - 1].trim();
      return lastPart.split('\n')[0].trim() || raw.split('\n')[0];
    }

    // Si contiene "nested exception", tomar solo el mensaje principal
    if (raw.includes('nested exception')) {
      return raw.split(';')[0].trim();
    }

    // Limitar longitud
    if (raw.length > 200) {
      return raw.substring(0, 197) + '...';
    }

    return raw;
  }

  /**
   * Devuelve un mensaje amigable según el código HTTP.
   */
  private getMessageByStatus(status: number, fallback: string): string {
    const messages: Record<number, string> = {
      400: 'Datos inválidos. Revisa los campos del formulario.',
      401: 'Sesión expirada. Inicia sesión nuevamente.',
      403: 'No tienes permisos para realizar esta acción.',
      404: 'El recurso solicitado no fue encontrado.',
      409: 'Conflicto: el recurso ya existe o está en uso.',
      422: 'Los datos enviados no son válidos.',
      500: 'Error interno del servidor. Inténtalo más tarde.',
      502: 'El servidor no está disponible temporalmente.',
      503: 'Servicio no disponible. Inténtalo más tarde.'
    };
    return messages[status] || fallback;
  }
}
