import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { AuthService } from '../../../core/services/auth.service';
import { NotificationService } from '../../../shared/services/notification.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    ReactiveFormsModule, RouterLink,
    MatCardModule, MatFormFieldModule, MatInputModule, MatSelectModule,
    MatButtonModule, MatIconModule, MatProgressSpinnerModule
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  form: FormGroup;
  hidePassword = true;
  loading = false;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private notification: NotificationService
  ) {
    this.form = this.fb.group({
      // Backend: @NotBlank + @Size(min=2, max=150)
      nombre: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(150)]],
      // Backend: @NotBlank
      tipoDocumento: ['DNI', Validators.required],
      // Backend: @NotBlank + @Size(min=8, max=20)
      numDocumento: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(20)]],
      // Backend: @NotBlank
      direccion: ['', [Validators.required]],
      // Backend: @NotBlank + @Size(min=7, max=15)
      telefono: ['', [Validators.required, Validators.minLength(7), Validators.maxLength(15)]],
      // Backend: @NotBlank + @Email + @isExistsByEmail
      email: ['', [Validators.required, Validators.email]],
      // Backend: @NotBlank + @Size(min=4, max=100)
      password: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(100)]]
    });
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    this.loading = true;
    const data = { ...this.form.value, admin: false };
    this.authService.register(data).subscribe({
      next: () => {
        this.notification.success('Registro exitoso. Inicia sesión.');
        this.router.navigate(['/auth/login']);
      },
      error: (err) => {
        this.loading = false;
        this.notification.handleHttpError(err, 'Error al registrar');
      }
    });
  }
}
