import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { UserService } from '../user.service';
import { UsuarioResponse, RolResponse } from '../../../shared/domain/models/usuario.model';
import { NotificationService } from '../../../shared/services/notification.service';

@Component({
  selector: 'app-user-form',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatCheckboxModule
  ],
  templateUrl: './user-form.component.html',
  styleUrl: './user-form.component.scss'
})
export class UserFormComponent implements OnInit {
  form: FormGroup;
  isEdit: boolean;
  roles: RolResponse[] = [];

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private notification: NotificationService,
    private dialogRef: MatDialogRef<UserFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: UsuarioResponse | null
  ) {
    this.isEdit = !!data;
    this.form = this.fb.group({
      nombre: [data?.nombre || '', [Validators.required, Validators.minLength(2), Validators.maxLength(150)]],
      tipoDocumento: [data?.tipoDocumento || '', Validators.required],
      numDocumento: [data?.numDocumento || '', [Validators.required, Validators.minLength(8), Validators.maxLength(20)]],
      direccion: [data?.direccion || '', Validators.required],
      telefono: [data?.telefono || '', [Validators.required, Validators.minLength(7), Validators.maxLength(15)]],
      email: [data?.email || '', [Validators.required, Validators.email]],
      password: ['', this.isEdit ? [] : [Validators.required, Validators.minLength(4)]],
      admin: [false],
      roles: [[]]
    });

    if (this.isEdit && data?.role) {
      // Map existing roles to the form (just the names for selection)
      const roleNames = data.role.map(r => r.nombre);
      this.form.patchValue({ roles: roleNames });
    }
  }

  ngOnInit(): void {
    this.userService.getAllRoles().subscribe({
      next: (roles) => {
        this.roles = roles;
      },
      error: (err) => this.notification.handleHttpError(err, 'Error al cargar roles')
    });
  }

  onSave(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const val = this.form.value;
    // Map role names back to RolRequest objects for the backend
    const rolesRequest = val.roles.map((name: string) => ({ nombre: name }));

    const requestData = {
      ...val,
      roles: rolesRequest
    };

    // Note: This project currently only supports create in this form based on the task
    this.userService.create(requestData).subscribe({
      next: () => {
        this.notification.success('Usuario creado con éxito');
        this.dialogRef.close(true);
      },
      error: (err) => this.notification.handleHttpError(err, 'Error al guardar usuario')
    });
  }
}
