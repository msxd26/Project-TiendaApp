import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { CategoryService } from '../category.service';
import { CategoriaResponse } from '../../../shared/domain/models/categoria.model';
import { NotificationService } from '../../../shared/services/notification.service';

@Component({
  selector: 'app-category-form',
  standalone: true,
  imports: [ReactiveFormsModule, MatDialogModule, MatFormFieldModule, MatInputModule, MatButtonModule],
  templateUrl: './category-form.component.html',
  styleUrl: './category-form.component.scss'
})
export class CategoryFormComponent {
  form: FormGroup;
  isEdit: boolean;

  constructor(
    private fb: FormBuilder,
    private svc: CategoryService,
    private notif: NotificationService,
    private ref: MatDialogRef<CategoryFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: CategoriaResponse | null
  ) {
    this.isEdit = !!data;
    this.form = fb.group({
      // Backend: @NotBlank + @isExistsByCategoria (server-side)
      nombre: [data?.nombre || '', [Validators.required, Validators.minLength(1)]],
      // Backend: @NotBlank
      descripcion: [data?.descripcion || '', [Validators.required, Validators.minLength(1)]]
    });
  }

  onSave(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    const obs = this.isEdit
      ? this.svc.update(this.data!.idcategoria, this.form.value)
      : this.svc.create(this.form.value);

    obs.subscribe({
      next: () => {
        this.notif.success(this.isEdit ? 'Categoría actualizada' : 'Categoría creada');
        this.ref.close(true);
      },
      error: (e) => this.notif.handleHttpError(e, 'Error al guardar categoría')
    });
  }
}
