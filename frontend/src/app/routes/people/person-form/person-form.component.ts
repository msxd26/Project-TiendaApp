import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { PersonService } from '../person.service';
import { PersonaResponse } from '../../../shared/domain/models/persona.model';
import { NotificationService } from '../../../shared/services/notification.service';

@Component({
  selector: 'app-person-form',
  standalone: true,
  imports: [ReactiveFormsModule, MatDialogModule, MatFormFieldModule, MatInputModule, MatSelectModule, MatButtonModule, MatIconModule],
  templateUrl: './person-form.component.html',
  styleUrl: './person-form.component.scss'
})
export class PersonFormComponent {
  form: FormGroup;
  isEdit: boolean;

  constructor(
    private fb: FormBuilder,
    private svc: PersonService,
    private notif: NotificationService,
    private ref: MatDialogRef<PersonFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: PersonaResponse | null
  ) {
    this.isEdit = !!data;
    this.form = fb.group({
      // Backend: @NotBlank
      tipoPersona: [data?.tipoPersona || 'Cliente', Validators.required],
      // Backend: @NotBlank + @Size(min=2, max=150)
      nombre: [data?.nombre || '', [Validators.required, Validators.minLength(2), Validators.maxLength(150)]],
      // Backend: @NotBlank
      tipoDocumento: [data?.tipoDocumento || 'DNI', Validators.required],
      // Backend: @NotBlank + @Size(min=8, max=20)
      numDocumento: [data?.numDocumento || '', [Validators.required, Validators.minLength(8), Validators.maxLength(20)]],
      // Backend: @NotBlank
      direccion: [data?.direccion || '', Validators.required],
      // Backend: @NotBlank + @Size(min=7, max=15)
      telefono: [data?.telefono || '', [Validators.required, Validators.minLength(7), Validators.maxLength(15)]],
      // Backend: @NotBlank + @Email
      email: [data?.email || '', [Validators.required, Validators.email]]
    });
  }

  onSave(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    const obs = this.isEdit
      ? this.svc.update(this.data!.idpersona, this.form.value)
      : this.svc.create(this.form.value);

    obs.subscribe({
      next: () => {
        this.notif.success(this.isEdit ? 'Persona actualizada' : 'Persona creada');
        this.ref.close(true);
      },
      error: (e) => this.notif.handleHttpError(e, 'Error al guardar persona')
    });
  }
}
